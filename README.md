# Overview

<div align="center">

[![Build Status](https://travis-ci.org/astrapi69/jobj-copy.svg?branch=develop)](https://travis-ci.org/astrapi69/jobj-copy) 
[![Coverage Status](https://coveralls.io/repos/github/astrapi69/jobj-copy/badge.svg?branch=develop)](https://coveralls.io/github/astrapi69/jobj-copy?branch=develop) 
[![Open Issues](https://img.shields.io/github/issues/astrapi69/jobj-copy.svg?style=flat)](https://github.com/astrapi69/jobj-copy/issues) 
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/de.alpharogroup/jobj-copy/badge.svg)](https://maven-badges.herokuapp.com/maven-central/de.alpharogroup/jobj-copy)
[![Javadocs](http://www.javadoc.io/badge/de.alpharogroup/jobj-copy.svg)](http://www.javadoc.io/doc/de.alpharogroup/jobj-copy)
[![MIT license](http://img.shields.io/badge/license-MIT-brightgreen.svg?style=flat)](http://opensource.org/licenses/MIT)

</div>

Utility library for simply copy java objects.

If you like this project put a ⭐ and donate.

# Donations

If you like this library, please consider a donation through paypal: <a href="https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=MJ7V43GU2H386" target="_blank">
<img src="https://www.paypalobjects.com/en_US/GB/i/btn/btn_donateCC_LG.gif" alt="PayPal this" title="PayPal – The safer, easier way to pay online!" border="0" />
</a>

or over bitcoin or bitcoin-cash with:

36JxRRDfRazLNqUV6NsywCw1q7TK38ukpC

or over ether with:

0x588Aa02De98B1Ef70afeDC3ec5290130a3E5e273

or over the donation buttons at the top.

## Note

No animals were harmed in the making of this library.

## License

The source code comes under the liberal MIT License, making jobj-copy great for all types of applications.

## Maven dependency

Maven dependency is now on sonatype.
Check out [sonatype repository](https://oss.sonatype.org/index.html#nexus-search;gav~de.alpharogroup~jobj-copy~~~) for latest snapshots and releases.

Add the following maven dependency to your project `pom.xml` if you want to import the core functionality of jobj-copy:

Than you can add the dependency to your dependencies:

	<properties>
			...
		<!-- JOBJ-COPY version -->
		<jobj-copy.version>3.2</jobj-copy.version>
			...
	</properties>
			...
		<dependencies>
			...
			<!-- JOBJ-COPY DEPENDENCY -->
			<dependency>
				<groupId>de.alpharogroup</groupId>
				<artifactId>jobj-copy</artifactId>
				<version>${jobj-copy.version}</version>
			</dependency>
			...
		</dependencies>
	
			
## gradle dependency

You can first define the version in the ext section and add than the following gradle dependency to your project `build.gradle` if you want to import the core functionality of jobj-copy:

```
ext {
			...
    jobjCopyVersion = "3.2"
			...
}
dependencies {
			...
compile "de.alpharogroup:jobj-copy:${jobjCopyVersion}"
			...
}
```

## Semantic Versioning

The versions of jobj-copy are maintained with the Simplified Semantic Versioning guidelines.

Release version numbers will be incremented in the following format:

`<major>.<minor>.<patch>`

For detailed information on versioning for this project you can visit this [wiki page](https://github.com/lightblueseas/mvn-parent-projects/wiki/Simplified-Semantic-Versioning).

## Want to Help and improve it? ###

The source code for jobj-copy are on GitHub. Please feel free to fork and send pull requests!

Create your own fork of [astrapi69/jobj-copy/fork](https://github.com/astrapi69/jobj-copy/fork)

To share your changes, [submit a pull request](https://github.com/astrapi69/jobj-copy/pull/new/develop).

Don't forget to add new units tests on your changes.

## Contacting the Developers

Do not hesitate to contact the jobj-copy developers with your questions, concerns, comments, bug reports, or feature requests.
- Feature requests, questions and bug reports can be reported at the [issues page](https://github.com/astrapi69/jobj-copy/issues).

## Credits

|**Travis CI**|
|     :---:      |
|[![Travis CI](https://travis-ci.com/images/logos/TravisCI-Full-Color.png)](https://coveralls.io/github/astrapi69/jobj-copy?branch=master)|
|Special thanks to [Travis CI](https://travis-ci.org) for providing a free continuous integration service for open source projects|
|     <img width=1000/>     |

|**Nexus Sonatype repositories**|
|     :---:      |
|[![sonatype repository](https://img.shields.io/nexus/r/https/oss.sonatype.org/de.alpharogroup/jobj-copy.svg?style=for-the-badge)](https://oss.sonatype.org/index.html#nexus-search;gav~de.alpharogroup~jobj-copy~~~)|
|Special thanks to [sonatype repository](https://www.sonatype.com) for providing a free maven repository service for open source projects|
|     <img width=1000/>     |

|**coveralls.io**|
|     :---:      |
|[![Coverage Status](https://coveralls.io/repos/github/astrapi69/jobj-copy/badge.svg?branch=develop)](https://coveralls.io/github/astrapi69/jobj-copy?branch=master)|
|Special thanks to [coveralls.io](https://coveralls.io) for providing a free code coverage for open source projects|
|     <img width=1000/>     |

|**javadoc.io**|
|     :---:      |
|[![Javadocs](http://www.javadoc.io/badge/de.alpharogroup/jobj-copy.svg)](http://www.javadoc.io/doc/de.alpharogroup/jobj-copy)|
|Special thanks to [javadoc.io](http://www.javadoc.io) for providing a free javadoc documentation for open source projects|
|     <img width=1000/>     |
