name := "cgroups-memory-enabled"
scalaVersion := "2.11.11"

enablePlugins(SbtReactiveAppPlugin)

rpMemory := 1048576
rpEnableCGroupMemoryLimit := true

TaskKey[Unit]("check") := {
  val outputDir = (stage in Docker).value
  val contents = IO.read(outputDir / "opt" / "docker" / "conf" / "application.ini")
  val lines = Seq(
    """-XX:+UnlockExperimentalVMOptions""",
    """-XX:+UseCGroupMemoryLimitForHeap"""
  )

  lines.foreach { line =>
    if (!contents.contains(line)) {
      sys.error(s"""Dockerfile is missing line "$line"""")
    }
  }
}
