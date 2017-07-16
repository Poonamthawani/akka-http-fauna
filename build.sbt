
name := "akka-http-fauna"

version := "0.1.0 "

scalaVersion := "2.11.5"



val log4j     = "2.8.2"
val akkaLog4j = "1.4.0"
val jacksonVersion = "2.8.8"
val jacksonDocVersion = "2.8"
val jodaTimeVersion = "2.9.4"
val jodaConvert = "1.8.1"

libraryDependencies ++= Seq(
  "com.google.protobuf" % "protobuf-java" % "2.5.0",
  "com.fasterxml.jackson.core" % "jackson-databind"                 % "2.8.8.1", // for using Log4j2's JsonLayout
  "com.iheart"                 %% "ficus"                           % "1.4.0" % "compile,test",
  "de.heikoseeberger"          %% "akka-log4j"                      % akkaLog4j,
  "com.typesafe.akka"          %% "akka-http"                       % "10.0.5",
  "com.typesafe.akka"          %% "akka-stream-kafka"               % "0.16",
  "com.typesafe.akka"          %% "akka-stream"                     % "2.5.1",
  "de.heikoseeberger"          %% "akka-http-circe"                 % "1.15.0",
  "io.circe"                   %% "circe-generic"                   % "0.7.1",
  "nl.grons"                   %% "metrics-scala"                   % "3.5.6",
  "com.typesafe.akka"          %% "akka-http-testkit"               % "10.0.6" % "test",
  "org.scalatest"              %% "scalatest"                       % "3.0.3" % "test",
  "net.manub"                  %% "scalatest-embedded-kafka"        % "0.13.0" % "test" exclude ("log4j", "log4j"),
  "org.apache.kafka"           %% "kafka"                           % "0.10.2.1" % "test" exclude ("log4j", "log4j") exclude ("org.slf4j", "slf4j-log4j12"),
  "org.apache.logging.log4j"   % "log4j-1.2-api"                    % "2.8.2" % "compile,test",
  "com.whisk"                  %% "docker-testkit-scalatest"        % "0.9.1" % "test",
  "com.whisk"                  %% "docker-testkit-impl-docker-java" % "0.9.1" % "test",
  "org.scalacheck"             %% "scalacheck"                      % "1.13.5" % "test",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.3",
  "com.faunadb" %% "faunadb-scala" % "1.0.0",
  "com.fasterxml.jackson.core" % "jackson-annotations" % jacksonVersion,
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion,
  "joda-time" % "joda-time" % jodaTimeVersion,
  "org.joda" % "joda-convert" % jodaConvert,
  "ch.qos.logback" % "logback-classic" % "1.1.3" % "test",
  "org.scalatest" %% "scalatest" % "3.0.3" % "test"
)

scalacOptions ++= Seq("-feature")
