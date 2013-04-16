package org.openworm.simulationengine.model.sph.x;

import java.util.ArrayList;

import org.openworm.simulationengine.core.model.IModel;
import org.openworm.simulationengine.model.sph.Connection;
import org.openworm.simulationengine.model.sph.SPHCell;
import org.openworm.simulationengine.model.sph.SPHModel;
import org.openworm.simulationengine.model.sph.SPHParticle;
import org.openworm.simulationengine.model.sph.Vector3D;

public class SPHModelX extends SPHModel implements IModel, Comparable<SPHModelX> {
	
	
	public SPHModelX(float xMax, float xMin, float yMax, float yMin, float zMax, float zMin){
		this.xMax = xMax;
		this.xMin = xMin;
		this.yMax = yMax;
		this.yMin = yMin;
		this.zMax = zMax;
		this.zMin = zMin;
		this.connections = new ArrayList<Connection>();
		this.particles = new ArrayList<SPHParticle>();
		this.cells = new ArrayList<SPHCell>();
	}
	
	public int getNumberOfParticles(){
		return particles.size();
	}
	
	public SPHModelX() {
		super();
	}

	public SPHModelX(SPHModel sphModel)
	{
		
		this.xMax = sphModel.getXMax();
		this.xMin = sphModel.getXMin();
		this.yMax = sphModel.getYMax();
		this.yMin = sphModel.getYMin();
		this.zMax = sphModel.getZMax();
		this.zMin = sphModel.getZMin();
		this.connections = new ArrayList<Connection>();
		this.particles = new ArrayList<SPHParticle>();
		this.cells = new ArrayList<SPHCell>();
		for(SPHParticle p:sphModel.getParticles())
		{
			getParticles().add(new SPHParticleX(p));
		}
		for(SPHCell c:sphModel.getCells())
		{
			getCells().add(c);
		}
		for(Connection c:sphModel.getConnections())
		{
			getConnections().add(c);
		}
	}

	public String getId() {
		return "sph";
	}

	@Override
	public int compareTo(SPHModelX o) {
		int different=0;
		Object[] parray= particles.toArray();
		Object[] poarray= o.particles.toArray();
		for(int i=0;i<parray.length;i++)
		{
			Vector3D p=((SPHParticleX)parray[i]).getPositionVector();
			Vector3D v=((SPHParticleX)parray[i]).getVelocityVector();
			
			Vector3D po=((SPHParticleX)poarray[i]).getPositionVector();
			Vector3D vo=((SPHParticleX)poarray[i]).getVelocityVector();
			
			boolean sameP=
					p.getX().equals(po.getX()) &&
					p.getY().equals(po.getY()) &&
					p.getZ().equals(po.getZ()) &&
					p.getP().equals(po.getP());
			boolean sameV=
					v.getX().equals(vo.getX()) &&
					v.getY().equals(vo.getY()) &&
					v.getZ().equals(vo.getZ()) &&
					v.getP().equals(vo.getP());
			
			if(!(sameP && sameV)) 
			{
				different++;
			}
			
		}
		return different;
	}
	
}
