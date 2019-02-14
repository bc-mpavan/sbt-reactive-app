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

package com.lightbend.rp.sbtreactiveapp

import scala.collection.{ Seq => DefaultSeq }
import scala.collection.immutable.Seq

sealed trait Ingress {
  def ingressPorts: Seq[Int]
}

case class PortIngress(ingressPorts: Seq[Int]) extends Ingress

object PortIngress {
  def apply(ports: Int*): PortIngress = new PortIngress(ports.toVector)
}

case class HttpIngress(ingressPorts: Seq[Int], hosts: Seq[String], paths: Seq[String]) extends Ingress {
  def ++(that: HttpIngress): HttpIngress =
    HttpIngress(ingressPorts ++ that.ingressPorts, hosts = hosts ++ that.hosts, paths = paths ++ that.paths)
}

object HttpIngress {
  def apply(paths: DefaultSeq[String]): HttpIngress =
    new HttpIngress(Vector.empty, Vector.empty, paths.toVector)

  def apply(hosts: DefaultSeq[String], paths: DefaultSeq[String]): HttpIngress =
    new HttpIngress(Vector.empty, hosts.toVector, paths.toVector)

  def apply(ingressPorts: DefaultSeq[Int], hosts: DefaultSeq[String], paths: DefaultSeq[String]): HttpIngress =
    new HttpIngress(ingressPorts.toVector, hosts.toVector, paths.toVector)
}
