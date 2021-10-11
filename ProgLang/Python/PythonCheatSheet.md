# Process Files:
## Process general files:
```
import os
if not os.path.exists('my_folder'):
    os.makedirs('my_folder')
```
## Process CSV:
```
import csv
with open('innovators.csv', 'r') as file:
    reader = csv.reader(file)
    for row in reader:
        print(row)
```
## Process JSON:

## Process YAML:

