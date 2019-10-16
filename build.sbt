ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

val thirdDependencies = Seq(
  "org.typelevel" %% "cats-core" % "2.0.0-M4",
  "org.typelevel" %% "cats-effect" % "1.3.1",
  "org.scalatest" %% "scalatest" % "3.0.5"
)



lazy val root = (project in file("."))
  .settings(
    sbtPlugin := true,
    name := "quant",
    libraryDependencies ++= thirdDependencies
  )
