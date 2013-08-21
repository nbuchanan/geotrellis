package geotrellis.raster

import geotrellis._
import geotrellis.raster.op._

trait RasterSourceLike[+Repr <: DataSource[Raster]] extends DataSourceLike[Raster, Repr] with DataSource[Raster] { self: Repr =>
  //type BF:CBSF[Repr, Raster, That]
  type CBF[A] = ({ type BF = CBSF[Repr, Raster, A]})#BF
  def tiles = self.partitions
  def rasterDefinition:Op[RasterDefinition]

  def get = {
    rasterDefinition.flatMap { rd =>
      val re = rd.re
      logic.Collect(rd.tiles).map(s => Raster(TileArrayRasterData(s.toArray, rd.tileLayout, re),re))
    }}
      
      
def converge = LocalRasterSource.fromRaster(
    rasterDefinition.flatMap { rd =>
      val re = rd.re
      logic.Collect(rd.tiles).map(s => Raster(TileArrayRasterData(s.toArray,rd.tileLayout,re), re))
    })
 
  def localAdd[That](i: Int)(implicit cbf: CBF[That]) = {
    this.map(MyAddConstant(_, i))
  }
}

case class MyAddConstant(r:Op[Raster], i:Op[Int]) extends Op2(r,i) ({
  (r,i) => Result(r.mapIfSet(z => z + i))
})


//case class StitchRasterDefinition
