/*
 * Copyright 2016 Azavea
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

package geotrellis.pointcloud.spark.io

import geotrellis.pointcloud.spark.ProjectedExtent3D
import geotrellis.pointcloud.spark.json._
import spray.json._

trait PointCloudHeader {
  val metadata: String
  val schema: String

  def projectedExtent3D = metadata.parseJson.convertTo[ProjectedExtent3D]
  def extent3D = projectedExtent3D.extent3d
  def extent = projectedExtent3D.extent3d.toExtent
  def crs = projectedExtent3D.crs
}
