organization := "giogt.cats"
scalaVersion := "2.12.8"

lazy val v = new {
  val slf4j   = "1.7.25"
  val logback = "1.2.3"

  val cats       = "1.6.0"
  val catsEffect = "1.2.0"

  val fs2 = "1.0.4"

  val enumeratum     = "1.5.13"
  val enumeratumCats = "1.5.15"

  val refined       = "0.9.4"
  val kindProjector = "0.9.9"
  val silencer      = "1.3.1"

  val scalaTest = "3.0.7"
}

lazy val appDependencies = Seq(
  // logger
  "org.slf4j"      % "slf4j-api"       % v.slf4j,
  "ch.qos.logback" % "logback-classic" % v.logback,
  // cats
  "org.typelevel" %% "cats-core"   % v.cats,
  "org.typelevel" %% "cats-effect" % v.catsEffect,
  // fs2
  "co.fs2" %% "fs2-core" % v.fs2,
  "co.fs2" %% "fs2-io"   % v.fs2,
  // enumeratum
  "com.beachape" %% "enumeratum"      % v.enumeratum,
  "com.beachape" %% "enumeratum-cats" % v.enumeratumCats,
  // refined
  "eu.timepit" %% "refined"      % v.refined,
  "eu.timepit" %% "refined-cats" % v.refined,
  // silencer
  "com.github.ghik" %% "silencer-lib" % v.silencer
)

lazy val testDependencies = Seq(
  "org.scalatest" %% "scalatest" % v.scalaTest % "test"
)

lazy val compilerPluginsDependencies = Seq(
  compilerPlugin(
    "org.spire-math" %% "kind-projector" % v.kindProjector cross CrossVersion.binary
  ),
  compilerPlugin("com.github.ghik" %% "silencer-plugin" % v.silencer)
)

libraryDependencies ++= appDependencies ++ testDependencies ++ compilerPluginsDependencies

fork        := true
cancelable  := true
logBuffered := false

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-explaintypes",
  "-Yrangepos",
  "-feature",
  "-Xfuture",
  "-Ypartial-unification",
  "-language:higherKinds",
  "-language:existentials",
  "-unchecked",
  "-Yno-adapted-args",
  "-Xlint:_,-type-parameter-shadow",
  "-Xsource:2.13",
  "-Ywarn-dead-code",
  "-Ywarn-inaccessible",
  "-Ywarn-infer-any",
  "-Ywarn-nullary-override",
  "-Ywarn-nullary-unit",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfatal-warnings",
  "-Ywarn-unused:imports",
  "-Ywarn-unused:_,imports",
  "-opt-warnings",
  "-Xlint:constant",
  "-Ywarn-extra-implicit"
)
