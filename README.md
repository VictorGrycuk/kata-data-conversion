# CLI Parser Kata

## Introduction
This is a simple command line parser that takes different kind and format of inputs and exports them to other format and destinations.

Currently, it has the following functions:
- Read and export records in `csv`, `json`, and `xml` format
  - The records contain the following fields:
    - name
    - address
    - stars
    - contact
    - phone
    - url
- Read from and export to a file, online content, or directly from the command line
	- The export to an online destination has not been implemented yet
- Validation of the information before exporting them:
	- Validate that a hotel name is not over a hundred characters long
	- Validate that a rating is a valid integer, as well as it is between the range of 0 and 5
	- Validate that the URL has a valid URL format

## Arguments
```bash
Usage: parser options_list
Options: 
    --inputSource, -i -> Source location of the input content (absolute file path for files). { String }
    --inputType, -t [CLI] -> The type of source for the input content { Value should be one of [file, cli, http] }
    --inputSerialization, -f [CSV] -> Serialization format of the input content { Value should be one of [csv, json, xml] }
    --outputDestination, -o -> Destination for the output content (absolute file path for files) { String }
    --outputType, -d [CLI] -> The type of destination for the output content { Value should be one of [file, cli, http] }
    --outputSerialization, -s [JSON] -> Serialization format for the output content { Value should be one of [csv, json, xml] }
    --printReport, -p [false] -> if true, it will export a JSON file of the invalid records and their issues 
    --help, -h -> Usage info 
```

---

### --inputSource, -i

The location of the source content.

In case of local files, it must be the absolute path to the file. In case of HTTP content, it must be the address of the online content.

#### Example

```bash
parser.jar --inputSource C:\Data\input.csv -t file -f CSV
```

The above example will read the file `C:\Data\input.csv`, process the data, and export the result to the command line.

---

### --inputType, -t

The type of source for the input content. It can be `file`, `cli`, or `http`. Defaults to **cli**.

- `file`: It will read the content  of the file provided by the argument `--inputSource`
- `cli`: It will read command line lines until the user presses `ctrol+d` (IntelliJ) or `cntrol+z` (Windows)
- `http`: It will read the content of the address provided by the argument `--inputSource`

#### Example
```bash
parser.jar -i https://pastebin.com/raw/Db8DKaCJ --inputType http -f CSV -s xml
```

The above example will read the http content at `https://pastebin.com/raw/Db8DKaCJ`, process the data, and export the result to the command line as XML.

---

### --inputSerialization, -f

Serialization format of the input content. It can be `csv`, `json`, or `xml`. Defaults to **csv**.

This will tell the parser how it should read the source content.

#### Example
```bash
parser.jar -t cli --inputSerialization CSV
```

The above example will read the input from the command line as CSV, process the data, and export the result to the command line as Json.

---

### --outputDestination, -o

The location for the destination content.

In case of local files, it must be the absolute path to the file. In case of HTTP content, it must be the address of the online content.

> **Note:** But for the sake of simplicity, this has not been implemented for this case study

#### Example

```bash
parser.jar --outputDestination C:\Data\data.xml -d file -s xml
```

The above example will read the input from the command line as CSV, process the data, and export the result to the file `C:\Data\input.xml` as XML.

---

### --outputType, -d

Similar to `--inputType`, it defines the type of the source for the output content. It can be `file`, `cli`, or `http`. Defaults to **cli**.

- `file`: It will write the content  to the file  path provided by the argument `--outputDestination`
- `cli`: It will write the content to the command line
- `http`: Currently not implemented. See note at `--outputDestination`
#### Example

```bash
parser.jar -i https://pastebin.com/raw/Db8DKaCJ -t http -o C:\Data\data.json --outputType file -s json
```

The above example will read the http content at `https://pastebin.com/raw/Db8DKaCJ`, process the data, and export the result to the file `C:\Data\input.json` as Json.

---

### --outputSerialization, -s

Similar to `--inputSerialization`, it is the serialization format for the output content. It can be `csv`, `json`, or `xml`. Defaults to **csv**.

This will tell the parser how it should serialize the output content.

#### Example

```bash
parser.jar -i C:\Data\data.json -t file -f json -o C:\Data\data.csv -d file --outputSerialization csv
```

The above example will read the file `C:\Data\input.csv`, process the data, and export the result to the file `C:\Data\input.json` as csv.

---

### --printReport, -p

If `true`, it will export a list of invalid records as a prettified Json file with a list of issues for each record. Defaults to **false**.

#### Example

```bash
parser.jar --printReport true
```

The above example will read the input from the command line as CSV, process the data, export the result to the command line as Json, and generate a file named `invalid-records.json` at the folder where the parser is executed.

---

## Record Validation

Any record that breaks any of the configured rules will be flagged as an invalid record, and will not be exported.

See `--printReport` for more information.

### Hotel Name rule

The Hotel Name rule validates that the name property for a given record is not greater than a given number of characters, which by default it is 100 characters.

### Valid Rating rule

The Valid Rating rule checks that the rating of a given record is a valid integer and is between a given valid range, which by default it is between 0 and 5, inclusive.

### Valid URL rule

The Valid URL rule checks that the URL of a given record has a valid URL syntax according to [org.apache.commons.validator.routines.UrlValidator](https://commons.apache.org/proper/commons-validator/apidocs/org/apache/commons/validator/routines/UrlValidator.html).

## Running the tool in docker

A `Dockerfile` was provided to ease the testing of the tool.

Once the image is built and ran, it is required to start a terminal (CLI button) from the running image, and validate that it is running by calling the tool as

```bash
java -jar parser.jar -h
```

## Kata analysis

The tool has been designed to be very flexible, having in mind the following points:

- The origin and destination of the content
- The content format/serialization for both source and destination

For the first point, the core app uses the interface `IOHandler` and delegates the responsibility to their concrete classes (which are injected by `ParserDI`).

For the second point, the core app simply uses the interface `Serializer` to delegate how the content should be handled.

Having the previous points correctly abstracted allowed combination for input, output, and serialization.

To a new implementation for any of the above, besides the implementation of the corresponding interfaces, all that is required is to update both the corresponding `enum` and the `ParserDI` to successfully inject the new implementation.



The validation rules are similar, as they implement the `rule` interface and are injected into the `RecordValidator` through the `ParserDI`.

## Development Environment

The application was developed and tested using **Windows 10**, using OpenJDK 16.

IntelliJ IDEA on Windows 10:

- IntelliJ IDEA 2021.1.3 (Community Edition)
- Build #IC-211.7628.21, built on June 30, 2021
- Runtime version: 11.0.11+9-b1341.60 amd64
- VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.
- Windows 10 10.0
- GC: G1 Young Generation, G1 Old Generation
- Memory: 2048M
- Cores: 8
- Non-Bundled Plugins:
  - org.jetbrains.kotlin (211-1.5.30-release-408-IJ7442.40)
- Kotlin: 211-1.5.30-release-408-IJ7442.40



It has also been tested on **macOS**:

- IntelliJ IDEA 2021.2.1 (Community Edition)
- Build #IC-212.5080.55, built on August 24, 2021
- Runtime version: 11.0.11+9-b1504.16 x86_64
- VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.
- macOS 11.4
- GC: G1 Young Generation, G1 Old Generation
- Memory: 2048M
- Cores: 8
- Non-Bundled Plugins:
  - com.wakatime.intellij.plugin (13.1.0)
  - Key Promoter X (2021.2), Docker (212.5080.8)
  - org.jetbrains.kotlin (212-1.5.30-release-409-IJ4638.7),
  - com.nbadal.ktlint (0.8.1), zielu.gittoolbox (212.7.7)
- Kotlin: 212-1.5.30-release-409-IJ4638.7

The Dockerfile was tested on macOS with **Docker Version 3.5.2 (3.5.2.18)**.
