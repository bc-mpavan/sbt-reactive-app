lazy val root = (project in file("."))
  .enablePlugins(SbtReactiveAppPlugin)
  .settings(
    name := "akka-cluster-bootstrapping-adds-endpoint",
    scalaVersion := "2.11.11",
    rpEnableAkkaClusterBootstrap := true,
    rpAkkaClusterBootstrapEndpointName := "my-akka-remote",
    TaskKey[Unit]("check") := {
      assert(rpEndpoints.value.contains(TcpEndpoint("my-akka-remote", 2552)))

      val outputDir = (stage in Docker).value
      val contents = IO.read(outputDir / "Dockerfile")
      val lines = Seq(
        """com.lightbend.rp.app-type="basic"""",
        """com.lightbend.rp.modules.akka-cluster-bootstrapping.enabled="true"""",
        """com.lightbend.rp.modules.common.enabled="true"""",
        """com.lightbend.rp.modules.secrets.enabled="false""""
      )

      lines.foreach { line =>
        if (!contents.contains(line)) {
          sys.error(s"""Dockerfile is missing line "$line"""")
        }
      }
    }
  )
