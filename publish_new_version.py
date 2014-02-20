# coding=utf-8

import xml.etree.ElementTree as ET
import subprocess
import sys
import re
import os, stat, mimetypes, httplib
import json

# 根据实际情况修改一个变量值
apk_file_path = "bin/eshare-android-preview-release.apk"

version_xml_file_path = "res/values/versions.xml"
tree = ET.parse(version_xml_file_path)
xml_root = tree.getroot()

def post_multipart(host, selector, fields, files):
    """
Post fields and files to an http host as multipart/form-data.
@param host: the hostname of the server to connect to.  For example: www.myserver.com
@param selector: where to go on the host.  For example: cgi-bin/myscript.pl or blog/upload, etc..
@param fields: a sequence of (name, value) elements for regular form fields.  For example:
    [("vals", "16,18,19"), ("foo", "bar")]
@param files: a sequence of (name, file) elements for data to be uploaded as files.  For example:
    [ ("mugshot", open("/images/me.jpg", "rb")) ]
@return: the server's response page.
    """

    content_type, body = _encode_multipart_formdata(fields, files)
    h = httplib.HTTPConnection(host)
    headers = {
        'User-Agent': 'python_multipart_caller',
        'Content-Type': content_type
        }
    h.request('POST', selector, body, headers)
    res = h.getresponse()
    return res.read()

def _encode_multipart_formdata(fields, files):
    """
@return: (content_type, body) ready for httplib.HTTP instance
    """

    BOUNDARY = '----------ThIs_Is_tHe_bouNdaRY_$'
    CRLF = '\r\n'
    L = []
    for (key, value) in fields:
        L.append('--' + BOUNDARY)
        L.append('Content-Disposition: form-data; name="%s"' % key)
        L.append('')
        L.append(value)
    for (key, fd) in files:
        file_size = os.fstat(fd.fileno())[stat.ST_SIZE]
        filename = fd.name.split('/')[-1]
        contenttype = mimetypes.guess_type(filename)[0] or 'application/octet-stream'
        L.append('--%s' % BOUNDARY)
        L.append('Content-Disposition: form-data; name="%s"; filename="%s"' % (key, filename))
        L.append('Content-Type: %s' % contenttype)
        fd.seek(0)
        L.append('\r\n' + fd.read())
    L.append('--' + BOUNDARY + '--')
    L.append('')
    body = CRLF.join(L)
    content_type = 'multipart/form-data; boundary=%s' % BOUNDARY
    return content_type, body

def first_verion_more_than_second(version1, version2):
  v1_arr = version1.split(".")
  v2_arr = version2.split(".")

  def to_int(x): return int(x)
  v1_arr = map(to_int, v1_arr)
  v2_arr = map(to_int, v2_arr)

  if(v1_arr[0] > v2_arr[0]): return True
  if(v1_arr[0] < v2_arr[0]): return False

  if(v1_arr[1] > v2_arr[1]): return True
  if(v1_arr[1] < v2_arr[1]): return False

  if(v1_arr[2] > v2_arr[2]): return True
  return False


def version_format_is_error(version):
  return (None == re.compile("^[0-9]+[\.][0-9]+[\.][0-9]+$").match(version))

def dir_is_commit():
  p = subprocess.Popen('git status', shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
  result = p.stdout.read()
  return (-1 != result.find("nothing to commit, working directory clean"))

def get_current_version_name():
  for el in xml_root.findall("string"):
    if el.get("name") == "versionName":
      return el.text

def set_version_name(version_name):
  for el in xml_root.findall("string"):
    if el.get("name") == "versionName":
      el.text = version_name

def update_version_code():
  for el in xml_root.findall("integer"):
    if el.get("name") == "versionCode":
      version_code = el.text
      new_version_code = int(version_code)+1
      el.text = str(new_version_code)

def update_manifest_xml_file(new_version):
  set_version_name(new_version)
  update_version_code()
  tree.write(version_xml_file_path)

def set_http_site(value):
  file_path = "res/values/http_site.xml"
  et = ET.parse(file_path)
  for el in et.getroot().findall("string"):
    if el.get("name") == "http_site":
      el.text = value
  et.write(file_path)


def main():
  if(not dir_is_commit()):
    print("git working directory is not clean, please run git commit first")
    sys.exit(1)

  upload_site_num = None
  print("select upload site")
  print("1 4ye")
  print("2 4ye-evaluation")
  while upload_site_num not in ["1","2"]:
    upload_site_num = raw_input('input (1/2):')

  if "1" == upload_site_num:
    upload_site = "4ye.mindpin.com"
    is_evaluation = False
    set_http_site("http://4ye.mindpin.com")

  if "2" == upload_site_num:
    upload_site = "4ye-evaluation.mindpin.com"
    is_evaluation = True
    set_http_site("http://4ye-evaluation.mindpin.com")

  new_version = raw_input('input new version:')
  if(version_format_is_error(new_version)):
    print("new version format is error")
    sys.exit(1)

  is_milestone_input = None
  correct_values = ["Y","y","N","n"]
  while is_milestone_input not in correct_values:
    is_milestone_input = raw_input('input is_milestone(Y/N):')

  if is_milestone_input in ["Y","y"]:
    is_milestone = "true"

  if is_milestone_input in ["N","n"]:
    is_milestone = "false"

  current_version = get_current_version_name()
  if(current_version == "1.0"): current_version = "0.0.0"

  if(not first_verion_more_than_second(new_version, current_version)):
    print("new version must more than %s" % current_version)
    sys.exit(1)


  update_manifest_xml_file(new_version)

  if is_evaluation:
    result = subprocess.call('ant clean release -f build_evaluation.xml', shell=True)
  else:
    result = subprocess.call('ant clean release -f build_release.xml', shell=True)

  if 0 != result:
    subprocess.call('git checkout .', shell=True)
    print("create apk failed, git checkout .")
    sys.exit(1)

  fields = [("is_milestone", is_milestone), ("version", new_version)]
  files  = [("package", open(apk_file_path,"rb"))]
  rp = post_multipart(upload_site, "/update/api/publish", fields, files)
  rp_hash = json.loads(rp)
  if "success" != rp_hash["status"]:
    subprocess.call('git checkout .', shell=True)
    print("%s, upload fail" % rp_hash["status"])
    sys.exit(1)

  if is_evaluation:
    subprocess.call('git add -A', shell=True)
    subprocess.call('git commit -m "release %s-eva version"' % new_version, shell=True)
    subprocess.call('git tag "%s-eva"' % new_version, shell=True)
    print("publish %s-eva" % new_version)
  else:
    subprocess.call('git add -A', shell=True)
    subprocess.call('git commit -m "release %s version"' % new_version, shell=True)
    subprocess.call('git tag %s' % new_version, shell=True)
    print("publish %s" % new_version)

  sys.exit(0)


if __name__ == "__main__":
  main()