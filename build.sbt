name := """play-scala-2.5"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

resolvers += Resolver.jcenterRepo

val playSwagger = "com.iheart" %% "play-swagger" % "0.3.2-PLAY2.5"
val swaggerUi = "org.webjars" % "swagger-ui" % "2.2.0"
val casbah = "org.mongodb" %% "casbah" % "2.8.1"
val jodaTime = "joda-time" % "joda-time" % "2.9.9"
val mockito = "org.mockito" % "mockito-core" % "1.10.19" % "test"

libraryDependencies ++= Seq(
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  playSwagger,
  swaggerUi,
  casbah,
  jodaTime,
  mockito
)

