## Running the project.

After you clone the project and before you commit a change, make sure the following
tasks are successful.
```
./gradlew clean
./gradlew build
./gradlew test
./gradle fatJar
```
The `fatJar` task above creates a fat jar with all the dependencies.
