sys.props.get("plugin.version") match {
  case Some(x) => addSbtPlugin("com.lightbend.rp" % "sbt-reactive-app" % x)
  case _ => sys.error("""|The system property 'plugin.version' is not defined.
                         |Specify this property using the scriptedLaunchOpts -D.""".stripMargin)
}

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.7.0")
