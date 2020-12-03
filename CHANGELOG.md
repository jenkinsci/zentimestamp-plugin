# zentimestamp-plugin Changelog

## [Unreleased]

## [4.2] - 2015-07-25

### Fixed

- [JENKINS-26958](https://issues.jenkins-ci.org/browse/JENKINS-26958): Workflow plugin compatibility.

## [4.1] - 2015-05-24

### Removed

- Comment out logging statement.

## [4.0] - 2015-04-07

### Fixed

- [JENKINS-26626](https://issues.jenkins-ci.org/browse/JENKINS-26626): Switch to use `BUILD_TIMESTAMP`
environment variable and make it compatible with Jenkins 1.597+.

## [3.3] - 2013-05-16

### Fixed

- [JENKINS-17975](https://issues.jenkins-ci.org/browse/JENKINS-17975): SCM polling stops to work if 
the previously used build node no longer exists.

## [3.2] - 2012-03-10

### Added

- Make it compatible with `MatrixProject`.

## [3.1] - 2012-03-07

### Added

- Make it compatible with **[envinject jenkins plugin](https://wiki.jenkins.io/display/JENKINS/EnvInject+Plugin)** 
(Remove `+RunListener+` and add an `+EnvironmentContributor+`).

### Fixed

- [JENKINS-12694](https://issues.jenkins-ci.org/browse/JENKINS-12694): Zentimestamp plugin v3.0 no 
longer compatible with Hudson.

## [3.0] - 2011-12-21

### Added

- Ability to change the date format at node level.

## [2.2] - 2011-03-01

### Changed

- UI labels from Hudson to Jenkins.

## [2.1] - 2011-02-19

### Changed

- New Jenkins 1.397 API and metadata.

## [2.0.1] - 2010-07-02

### Fixed

- Regression for backward compatibility with **Zentimestamp 1.2**.

## [2.0] - 2010-06-20

### Added

- The plugin is now a job property that enables users to change the `+BUILD_ID+` variable from Hudson publishers too.

## [1.2] - 2009-02-10

### Changed

- Upgraded to new Hudson API.

### Added

- Integration tests.

## [1.1] - 2009-06-10

### Fixed

- Help 404 error with Hudson 1.3xx.

## [1.0] - 2008-08-12

Initial release

[Unreleased]: https://github.com/jenkinsci/zentimestamp-plugin/compare/zentimestamp-4.2...master
[4.2]: https://github.com/jenkinsci/zentimestamp-plugin/compare/zentimestamp-4.1...zentimestamp-4.2
[4.1]: https://github.com/jenkinsci/zentimestamp-plugin/compare/zentimestamp-4.0...zentimestamp-4.1
[4.0]: https://github.com/jenkinsci/zentimestamp-plugin/compare/zentimestamp-3.3...zentimestamp-4.0
[3.3]: https://github.com/jenkinsci/zentimestamp-plugin/compare/zentimestamp-3.2...zentimestamp-3.3
[3.2]: https://github.com/jenkinsci/zentimestamp-plugin/compare/zentimestamp-3.1...zentimestamp-3.2
[3.1]: https://github.com/jenkinsci/zentimestamp-plugin/compare/zentimestamp-3.0...zentimestamp-3.1
[3.0]: https://github.com/jenkinsci/zentimestamp-plugin/compare/zentimestamp-2.2...zentimestamp-3.0
[2.2]: https://github.com/jenkinsci/zentimestamp-plugin/compare/zentimestamp-2.1...zentimestamp-2.2
[2.1]: https://github.com/jenkinsci/zentimestamp-plugin/compare/zentimestamp-2.0.1...zentimestamp-2.1
[2.0.1]: https://github.com/jenkinsci/zentimestamp-plugin/compare/zentimestamp-2.0...zentimestamp-2.0.1
[2.0]: https://github.com/jenkinsci/zentimestamp-plugin/compare/zentimestamp-1.2...zentimestamp-2.0
[1.2]: https://github.com/jenkinsci/zentimestamp-plugin/compare/zentimestamp-1.1...zentimestamp-1.2
[1.1]: https://github.com/jenkinsci/zentimestamp-plugin/compare/zentimestamp-1.0...zentimestamp-1.1
[1.0]: https://github.com/jenkinsci/zentimestamp-plugin/releases/tag/zentimestamp-1.0
