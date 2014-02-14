#!/usr/bin/env python

import xml.etree.ElementTree as ET
import os
import time

def main():
    generate_migration()

# manipulate manifest.xml
current_time = time.strftime("%Y%m%d%H%M%S", time.localtime())[2:-2]
versions = "res/values/versions.xml"
tree = ET.parse(versions)
root = tree.getroot()

def set_metadata_dbversion():
    for el in root.findall("integer"):
        if el.get("name") == "AA_DB_VERSION":
            el.text = str(current_time)

    tree.write(versions)

# generate empty migration sql file
migrations_dir = "assets/migrations/"

def check_or_make_migration_dir():
    if not os.path.isdir(migrations_dir):
        os.makedirs(migrations_dir)

def basename(file_name):
    return os.path.splitext(os.path.basename(file_name))[0]

def get_migrations():
    check_or_make_migration_dir()
    migrations = [basename(s) for s in os.listdir(migrations_dir)]
    migrations.sort(key=int)
    return migrations

def generate_migration():
    file_name = migrations_dir + current_time + ".sql"
    set_metadata_dbversion()
    open(file_name, 'a').close()

if __name__ == "__main__":
    main()