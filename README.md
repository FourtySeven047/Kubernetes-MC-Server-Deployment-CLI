
# Kubernetes Minecraft Server Deployment CLI

This projects is a simple CLI interface to quickly deploy multiple minecraft servers without a hassle.

## Features

- Easy to understand Configuration Wizards for deployment creation
- Wide range of changeable settings to satisfy your individual needs (version, port, naming)
- Functional on ARM and x64 machines
- Minecraft Version support for 1.18.0 and up!
- Delete deployments effortlessly
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

## Creating a Deployment

### Vanilla Server

To create a vanilla server deployment you need to run the following command:

```bash
  sudo java -jar KMSD-CLI-1.0.0-jar-with-dependencies.jar deploy vanilla
```

After that you will be guided through the configuration wizard. As soon as you have finished the wizard the deployment will be created and started.

### Other Server Types

Coming soon...

## Deleting a Deployment

Deleting a deployment is as easy as creating one. Just run the following command:

```bash
  sudo java -jar KMSD-CLI-1.0.0-jar-with-dependencies.jar delete deployment
```

After that you will see a list of all active deployments in a table. You need to select the deployment you want to delete by entering the number of the deployment. This number is shown in the first column of the table. After that you need to confirm the deletion by entering "yes" or "no". If you enter "yes" the deployment will be deleted. If you enter "no" the deletion will be canceled and you the CLI will exit.
Then the deployment will be deleted.

## Commands

To see all available commands run the following command:

```bash
  sudo java -jar KMSD-CLI-1.0.0-jar-with-dependencies.jar help
```

Alternative you can use the following table to see all available commands:

| Command           | Description                                                         |
|-------------------|---------------------------------------------------------------------|
| deploy vanilla    | Start the configuration wizard for a new vanilla server deployment. |
| delete deployment | Delete a active deployment                                          |
| config change     | Change configuration                                                |
| ps                | Print the status of all active deployments                          |
| help              | Show all available commands.                                        |

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