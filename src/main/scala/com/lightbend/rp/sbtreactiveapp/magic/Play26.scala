/*
 * Copyright 2017 Lightbend, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lightbend.rp.sbtreactiveapp.magic

import sbt.AutoPlugin

import scala.language.reflectiveCalls
import scala.util.Try

object Play26 {
  def playPlugin(classLoader: ClassLoader): Try[AutoPlugin] =
    withContextClassloader(classLoader) { loader =>
      getSingletonObject[AutoPlugin](loader, "play.sbt.Play$")
    }

  def version: Option[String] = {
    // The method signature equals the signature of `play.core.PlayVersion`
    type PlayVersion = {
      def current: String
    }

    withContextClassloader(this.getClass.getClassLoader) { loader =>
      getSingletonObject[PlayVersion](loader, "play.core.PlayVersion$")
        .map(_.current)
        .toOption
    }
  }
}
