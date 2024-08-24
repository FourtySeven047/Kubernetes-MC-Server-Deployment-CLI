
# Kubernetes Minecraft Server Deployment CLI

This projects is a simple CLI interface to quickly deploy multiple minecraft servers without a hassle.

## Features

- Easy to understand Configuration Wizards for deployment creation
- Wide range of changeable settings to satisfy your individual needs (version, port, naming)
- Functional on ARM and x64 machines
- Minecraft Version support for 1.18.0 and up!
- Fully automatic download of supported server jar files
- Autonomous booting of deployments

## Installation

At the moment this CLI only works on Ubuntu related Linux distros.

### Requirements

- Java 17
- Health Kubernetes instance using docker as container runtime (other runtimes may work as well)
- Internet connection

### Setup
First you need to download the latest release from the releases page. After that you need to make the file executable by running the following command in the terminal. Make sure to execute the command as root user.

```bash
  sudo java -jar KMSD-CLI-1.0.0-jar-with-dependencies.jar
```

If your output looks like this you are good to go:
```bash
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
No deployments found. Exiting...
```
Do not worry about the SLF4J warnings. They are not relevant for the functionality of the CLI.

## Usage 

Coming soon!

## Authors

- [@FourtySeven047](https://www.github.com/fourtyseven047)

## License

MIT License

Copyright (c) 2024 Thorben Buenger

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.