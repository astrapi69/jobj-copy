## Change log
----------------------

Version 4.2-SNAPSHOT
-------------

CHANGED:

- update of gradle to new version 8.4
- update of gradle plugin dependency io.freefair.gradle:lombok-plugin to new version 8.4
- update of gradle plugin dependency com.github.ben-manes.versions.gradle.plugin to new version 0.49.0
- update of gradle plugin dependency io.freefair.gradle:lombok-plugin to new version 6.22.0
- update of dependency jobj-core in new version 8.1
- update of dependency jackson-databind to new version 2.16.0-rc1
- update of test dependency test-object in new minor version 8.1

Version 4.1
-------------

ADDED:

- new dependency 'io.github.astrapi69:jobj-reflect' in version 2

CHANGED:

- update of dependency jobj-core in new version 8

Version 4
-------------

CHANGED:

- update of jdk to version 17
- update of gradle to new version 8.4-rc-1
- update of gradle plugin dependency io.freefair.gradle:lombok-plugin to new version 8.3
- update of gradle plugin dependency com.github.ben-manes.versions.gradle.plugin to new version 0.48.0
- update of dependency jobj-core in new version 7.1
- update of dependency jackson-databind to new version 2.15.2
- update of test dependency test-object in new minor version 7.2
- remove of dependency commons-beanutils

Version 3.7
-------------

ADDED:

- new dependency silly-io in new version 1.9
- new copy method that copies the given object to base64 encoded String object
- new copy method that copies a base64 encoded String object back to the origin object

CHANGED:

- update of dependency jackson-databind to new version 2.13.2

Version 3.6
-------------

ADDED:

- new method for copy an object to a map
- new method for copy a map to an object that was previously copied in the map
- new dependency jackson-databind in version 2.13.1

CHANGED:

- update of gradle to new version 7.4
- update of gradle plugin dependency io.freefair.gradle:lombok-plugin to new version 6.4.1
- update of gradle plugin dependency com.github.ben-manes.versions.gradle.plugin to new version 0.42.0
- update of dependency jobj-core in new version 5.3
- update of test dependency test-objects in new major version 6

Version 3.5
-------------

ADDED:

- new test-dependency junit-jupiter (junit 5) in version 5.8.2

CHANGED:

- remove of test-dependency testng
- update dependency of com.github.ben-manes.versions.gradle.plugin to new version 0.41.0
- update of dependency jobj-core in new version 5.2

Version 3.4
-------------

ADDED:

- new method for copy primitive and object arrays and enums

CHANGED:

- update of gradle to new version 7.3.3
- removed delegate method copyProperties

Version 3.3
-------------

CHANGED:

- migrated from maven to gradle build system
- migrated to new package 'io.github.astrapi69'

Version 3.2
-------------

ADDED:

- this changelog file
- created PULL_REQUEST_TEMPLATE.md file
- created CODE_OF_CONDUCT.md file
- created CONTRIBUTING.md file
- provide package.html for the javadoc of packages
- moved classes from obsolet jobject-copy project
