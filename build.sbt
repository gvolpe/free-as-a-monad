name := """free-as-a-monad"""

version := "1.0"

scalaVersion := "2.11.8"

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.4")

libraryDependencies ++= Seq(
  "co.fs2"        %% "fs2-core"     % "0.10.0-M2",
  "co.fs2"        %% "fs2-io"       % "0.10.0-M2",
  "org.typelevel" %% "cats-effect"  % "0.3",
  "org.typelevel" %% "cats-free"    % "0.9.0",
  "org.scalatest" %% "scalatest"    % "2.2.4" % "test"
)
