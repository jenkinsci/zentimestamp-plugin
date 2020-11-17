# zentimestamp-plugin Changelog

## Release 4.2 (July 25, 2015)

### Fixed
- Fix [JENKINS-26958](https://issues.jenkins-ci.org/browse/JENKINS-26958): workflow plugin compatibility

## Release 4.1 (May 24, 2015)

### Removed
- Commented out logging statement

## Release 4.0 (April 07, 2015)

### Fixed
- Fix [JENKINS-26626](https://issues.jenkins-ci.org/browse/JENKINS-26626) - Switch to use BUILD_TIMESTAMP environment variable and make it compatible with Jenkins 1.597+

## Release 3.3 (May 16, 2013)

### Fixed
- Fix [JENKINS-17975](https://issues.jenkins-ci.org/browse/JENKINS-17975) - SCM polling stops to work if the previously used build node no longer exists

## Release 3.2 (March 10, 2012)

### Added
- Make it compatible to MatrixProject

## Release 3.1 (March 07, 2012)

### Added
- Make it compatible to [envinject jenkins plugin](https://wiki.jenkins.io/display/JENKINS/EnvInject+Plugin)(Remove `+RunListener+` and add an `+EnvironmentContributor+`).

### Fixed
- Fix [JENKINS-12694](https://issues.jenkins-ci.org/browse/JENKINS-12694) - Zentimestamp plugin v3.0 no longer compatible with hudson.

## Release 3.0 (December 21, 2011)

### Added
- Add the ability to change the date format at node level

## Release 2.2 (March 1, 2011)

### Changed
- Change UI labels from Hudson to Jenkins

## Release 2.1 (February 19, 2011)

### Changed
- Updated over new Jenkins 1.397 API and metadata

## Release 2.0.1 (July 02, 2010)

### Fixed
- Fixed regression for backward compatibility with zentimestamp 1.2

## Release 2.0 (June 20, 2010)

### Added
- The plugin is now a job property that enables users to change the `+BUILD_ID+` variable from Hudson publishers too

## Release 1.2 (February 10, 2009)

### Changed
- Upgraded to new Hudson API 

### Added
- Added integration tests

## Release 1.1 (June 10, 2009)

### Fixed
- Fixed Help 404 error with Hudson 1.3xx

## Release 1.0 (August 12, 2008)

Initial release