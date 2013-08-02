package org.geppetto.model.sph.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.geppetto.core.model.MathUtils;
import org.geppetto.model.sph.Connection;
import org.geppetto.model.sph.SPHModel;
import org.geppetto.model.sph.SPHParticle;
import org.geppetto.model.sph.Vector3D;
import org.geppetto.model.sph.common.SPHConstants;
import org.geppetto.model.sph.x.SPHFactory;

public class SPHXMLCreator {


	private static final String SPH_XML = "./sphModel.xml";
	
	private static final int PARTICLE_COUNT = 296 + 216;
	public static final float XMIN = 0;
	public static final float XMAX = 120.24f;
	public static final float YMIN = 0;
	public static final float YMAX = 80.16f;
	public static final float ZMIN = 0;
	public static final float ZMAX = 182.03f;


	private static SPHModel createModel()
	{
		SPHFactory factory = new SPHFactory();

		SPHModel model = factory.createSPHModel();

		model.setXMax(XMAX);
		model.setXMin(XMIN);
		model.setYMax(YMAX);
		model.setYMin(YMIN);
		model.setZMax(ZMAX);
		model.setZMin(ZMIN);

		generateElasticScene(model);
		
		return model;
	}
	
	public static void generateBoundaries(SPHModel model){
		SPHFactory factory = new SPHFactory();
		
		// calculate number of boundary particles by X, Y, Z axis. Distance Between two neighbor particle is equal to 
		int n = (int)( ( XMAX - XMIN ) / SPHConstants.R0 ); //X
		int m = (int)( ( YMAX - YMIN ) / SPHConstants.R0 ); //Y
		int k = (int)( ( ZMAX - ZMIN ) / SPHConstants.R0 ); //Z
		
		float x,y,z;
		int i = 0;
		//drop
		//Creation of Boundary Particle
		x = XMIN;
		z = ZMIN;
		y = YMIN;
		float x1, y1, z1;
		y1 = YMAX;
		float speed = 1.0f;
		float normCorner = (float) (1/Math.sqrt(3.f));
		float normBoundary = (float) (1/Math.sqrt(2.f));
		boolean isBoundary = false;
		int count = 2 *( k * n +  n + k );
		for(;i <= count;i+=2)
		{
			Vector3D positionVector1 = factory.createVector3D();
			positionVector1.setX(x);
			positionVector1.setY(y);
			positionVector1.setZ(z);
			positionVector1.setP(3.1f); // 3 = boundary

			Vector3D positionVector2 = factory.createVector3D();
			positionVector2.setX(x);
			positionVector2.setY(y1);
			positionVector2.setZ(z);
			positionVector2.setP(3.1f); // 3 = boundary
			
			Vector3D velocityVector1 = factory.createVector3D();
			Vector3D velocityVector2 = factory.createVector3D();
			
			x+= SPHConstants.R0;		
			if(i == 0){
				velocityVector1.setX(normCorner);
				velocityVector1.setY(normCorner);
				velocityVector1.setZ(normCorner);
				velocityVector1.setP(0f);

				velocityVector2.setX(normCorner);
				velocityVector2.setY(-normCorner);
				velocityVector2.setZ(normCorner);
				velocityVector2.setP(0f);
				isBoundary = true;
			}
			if(x >= XMAX && z == ZMIN && !isBoundary){
				velocityVector1.setX(-normCorner);
				velocityVector1.setY(normCorner);
				velocityVector1.setZ(normCorner);
				velocityVector1.setP(0f);

				velocityVector2.setX(-normCorner);
				velocityVector2.setY(-normCorner);
				velocityVector2.setZ(normCorner);
				velocityVector2.setP(0f);
				isBoundary = true;
			}

			if(x >= XMAX && z >= ZMAX - SPHConstants.R0 && !isBoundary){
				velocityVector1.setX(-normCorner);
				velocityVector1.setY(normCorner);
				velocityVector1.setZ(-normCorner);
				velocityVector1.setP(0f);

				velocityVector2.setX(-normCorner);
				velocityVector2.setY(-normCorner);
				velocityVector2.setZ(-normCorner);
				velocityVector2.setP(0f);
				isBoundary = true;
			}
			if(x - SPHConstants.R0 == XMIN && z >= ZMAX - SPHConstants.R0 && !isBoundary){
				velocityVector1.setX(normCorner);
				velocityVector1.setY(normCorner);
				velocityVector1.setZ(-normCorner);
				velocityVector1.setP(0f);

				velocityVector2.setX(normCorner);
				velocityVector2.setY(-normCorner);
				velocityVector2.setZ(-normCorner);
				velocityVector2.setP(0f);
				isBoundary = true;
			}

			if(x >= XMAX && !isBoundary){
				velocityVector1.setX(-normBoundary);
				velocityVector1.setY(normBoundary);
				velocityVector1.setZ(0f);
				velocityVector1.setP(0f);

				velocityVector2.setX(-normBoundary);
				velocityVector2.setY(-normBoundary);
				velocityVector2.setZ(0f);
				velocityVector2.setP(0f);
				isBoundary = true;
			}

			if(z == ZMIN && !isBoundary){
				velocityVector1.setX(0f);
				velocityVector1.setY(normBoundary);
				velocityVector1.setZ(normBoundary);
				velocityVector1.setP(0f);

				velocityVector2.setX(0f);
				velocityVector2.setY(-normBoundary);
				velocityVector2.setZ(normBoundary);
				velocityVector2.setP(0f);
				isBoundary = true;
			}
			if(x - SPHConstants.R0 == XMIN && !isBoundary){
				velocityVector1.setX(normBoundary);
				velocityVector1.setY(normBoundary);
				velocityVector1.setZ(0f);
				velocityVector1.setP(0f);

				velocityVector2.setX(normBoundary);
				velocityVector2.setY(-normBoundary);
				velocityVector2.setZ(0f);
				velocityVector2.setP(0f);
				isBoundary = true;
			}
			if(z >= ZMAX - SPHConstants.R0 && !isBoundary){
				velocityVector1.setX(0f);
				velocityVector1.setY(normBoundary);
				velocityVector1.setZ(-normBoundary);
				velocityVector1.setP(0f);

				velocityVector2.setX(0f);
				velocityVector2.setY(-normBoundary);
				velocityVector2.setZ(-normBoundary);
				velocityVector2.setP(0f);
				isBoundary = true;
			}
			if(isBoundary == false){
				velocityVector1.setX(0f);
				velocityVector1.setY(speed);
				velocityVector1.setZ(0f);
				velocityVector1.setP(0f);

				velocityVector2.setX(0f);
				velocityVector2.setY(-speed);
				velocityVector2.setZ(0f);
				velocityVector2.setP(0f);
			}
			
			// add particles
			SPHParticle particle1 = factory.createSPHParticle();
			particle1.setPositionVector(positionVector1);
			particle1.setVelocityVector(velocityVector1);
			particle1.setMass(1f);
			model.getParticles().add(particle1);
			
			SPHParticle particle2 = factory.createSPHParticle();
			particle2.setPositionVector(positionVector2);
			particle2.setVelocityVector(velocityVector2);
			particle2.setMass(1f);
			model.getParticles().add(particle2);
			
			// update coordinates for next iteration
			isBoundary = false;
			if(x > XMAX) { 
				x = XMIN; 
				z += SPHConstants.R0; 
			}
		}

		x = XMIN;
		y = YMIN + SPHConstants.R0;
		z = ZMIN;

		x1 = XMAX;
		isBoundary = false;
		count = 2 *( k * ( m - 2 )  + k + m - 2) + i;
		for(;i <= count;i+=2)
		{
			Vector3D positionVector1 = factory.createVector3D();
			positionVector1.setX(x);
			positionVector1.setY(y);
			positionVector1.setZ(z);
			positionVector1.setP(3.1f); // 3 = boundary

			Vector3D positionVector2 = factory.createVector3D();
			positionVector2.setX(x1);
			positionVector2.setY(y);
			positionVector2.setZ(z);
			positionVector2.setP(3.1f); // 3 = boundary
			
			Vector3D velocityVector1 = factory.createVector3D();
			Vector3D velocityVector2 = factory.createVector3D();

			if(z == ZMIN){
				velocityVector1.setX(normBoundary);
				velocityVector1.setY(0f);
				velocityVector1.setZ(normBoundary);
				velocityVector1.setP(0f);

				velocityVector2.setX(-normBoundary);
				velocityVector2.setY(0f);
				velocityVector2.setZ(normBoundary);
				velocityVector2.setP(0f);
				isBoundary = true;
			}
			if(z >= ZMAX - SPHConstants.R0 && !isBoundary){
				velocityVector1.setX(normBoundary);
				velocityVector1.setY(0f);
				velocityVector1.setZ(-normBoundary);
				velocityVector1.setP(0f);

				velocityVector2.setX(-normBoundary);
				velocityVector2.setY(0f);
				velocityVector2.setZ(-normBoundary);
				velocityVector2.setP(0f);
				isBoundary = true;
			}
			if(isBoundary == false){
				velocityVector1.setX(speed);
				velocityVector1.setY(0f);
				velocityVector1.setZ(0f);
				velocityVector1.setP(0f);

				velocityVector2.setX(-speed);
				velocityVector2.setY(0f);
				velocityVector2.setZ(0f);
				velocityVector2.setP(0f);
			}
			
			// add particles
			SPHParticle particle1 = factory.createSPHParticle();
			particle1.setPositionVector(positionVector1);
			particle1.setVelocityVector(velocityVector1);
			particle1.setMass(1f);
			model.getParticles().add(particle1);
			
			SPHParticle particle2 = factory.createSPHParticle();
			particle2.setPositionVector(positionVector2);
			particle2.setVelocityVector(velocityVector2);
			particle2.setMass(1f);
			model.getParticles().add(particle2);
			
			// update coordinates for next iteration
			isBoundary = false;
			y+= SPHConstants.R0;

			if(y > YMAX - SPHConstants.R0) { 
				y = YMIN + SPHConstants.R0; z += SPHConstants.R0; 
			}
		}
		
		x = XMIN + SPHConstants.R0;
		y = YMIN + SPHConstants.R0;
		z = ZMIN;

		z1 = ZMAX;
		count = 2 *( ( n - 2 ) * ( m - 2 )  + n + m - 4) + i;
		for(;i <= count;i+=2)
		{
			Vector3D positionVector1 = factory.createVector3D();
			positionVector1.setX(x);
			positionVector1.setY(y);
			positionVector1.setZ(z);
			positionVector1.setP(3.1f); // 3 = boundary

			Vector3D positionVector2 = factory.createVector3D();
			positionVector2.setX(x);
			positionVector2.setY(y);
			positionVector2.setZ(z1);
			positionVector2.setP(3.1f); // 3 = boundary

			Vector3D velocityVector1 = factory.createVector3D();
			velocityVector1.setX(0f);
			velocityVector1.setY(0f);
			velocityVector1.setZ(speed);
			velocityVector1.setP(0f);

			Vector3D velocityVector2 = factory.createVector3D();
			velocityVector2.setX(0f);
			velocityVector2.setY(0f);
			velocityVector2.setZ(-speed);
			velocityVector2.setP(0f);
			
			// add particles
			SPHParticle particle1 = factory.createSPHParticle();
			particle1.setPositionVector(positionVector1);
			particle1.setVelocityVector(velocityVector1);
			particle1.setMass(1f);
			model.getParticles().add(particle1);
			
			SPHParticle particle2 = factory.createSPHParticle();
			particle2.setPositionVector(positionVector2);
			particle2.setVelocityVector(velocityVector2);
			particle2.setMass(1f);
			model.getParticles().add(particle2);
			
			// update coordinates for next iteration
			y+= SPHConstants.R0;

			if(y > YMAX - SPHConstants.R0) { 
				y = YMIN + SPHConstants.R0; x += SPHConstants.R0; 
			}
		}
	}
	
	public static void generateBottomLayerOfLiquid(SPHModel model)
	{
		SPHFactory factory = new SPHFactory();
		
		float coeff = 0.2325f; // for particle mass
		float x,y,z;
		x = SPHConstants.R0 * 5 + 0*XMAX/4 + SPHConstants.H*coeff;
		y = SPHConstants.R0 * 15 + SPHConstants.H*coeff;
		z = SPHConstants.R0 * 5 + SPHConstants.H*coeff;
		
		int pCount = model.getParticles().size();
		for( ; pCount < PARTICLE_COUNT; ++pCount )
		{
			Vector3D positionVector = factory.createVector3D();
			positionVector.setX(x);
			positionVector.setY(y);
			positionVector.setZ(z);
			positionVector.setP(1.1f); // 1 = liquid

			Vector3D velocityVector = factory.createVector3D();
			velocityVector.setX(0f);
			velocityVector.setY(0f);
			velocityVector.setZ(0f);
			velocityVector.setP(0f);
			
			// add particles
			SPHParticle particle = factory.createSPHParticle();
			particle.setPositionVector(positionVector);
			particle.setVelocityVector(velocityVector);
			particle.setMass(1f);
			model.getParticles().add(particle);
			
			// update coordinates for next iteration
			x+= 2*SPHConstants.H*coeff;

			if(x>XMAX/2) { 
				x = SPHConstants.R0 * 5 + SPHConstants.H*coeff; z += 2*SPHConstants.H*coeff; 
			}
			if(z>ZMAX/2) { 
				x = SPHConstants.R0 * 5 + SPHConstants.H*coeff; z = SPHConstants.R0 * 5 + SPHConstants.H*coeff; y += 2*SPHConstants.H*coeff; 
			}
		}
	}
	
	public static void generateRandomLiquidConfiguration(SPHModel model)
	{
		SPHFactory factory = new SPHFactory();
		
		int pCount = model.getParticles().size();
		for( ; pCount < PARTICLE_COUNT; pCount++ )
		{
			float r = ((float)MathUtils.randomGenerator.nextInt(SPHConstants.RAND_MAX) / (float)SPHConstants.RAND_MAX );

			Vector3D positionVector = factory.createVector3D();
			positionVector.setX(MathUtils.scale(XMIN + 1, (XMAX -1)/10 , r)); 
			r = ((float)MathUtils.randomGenerator.nextInt(SPHConstants.RAND_MAX) / (float)SPHConstants.RAND_MAX );
			positionVector.setY(MathUtils.scale(YMIN + 1, YMAX - 1 , r)); 
			r = ((float)MathUtils.randomGenerator.nextInt(SPHConstants.RAND_MAX) / (float)SPHConstants.RAND_MAX );
			positionVector.setZ(MathUtils.scale(ZMIN + 1, ZMAX -1, r));
			positionVector.setP(1.1f);
			
			r = ((float)MathUtils.randomGenerator.nextInt(SPHConstants.RAND_MAX) / (float)SPHConstants.RAND_MAX );
			
			Vector3D velocityVector = factory.createVector3D();
			velocityVector.setX(MathUtils.scale(-1.0f, 1.0f, r));
			r = ((float)MathUtils.randomGenerator.nextInt(SPHConstants.RAND_MAX) / (float)SPHConstants.RAND_MAX );
			velocityVector.setY(MathUtils.scale(-1.0f, 1.0f, r));
			r = ((float)MathUtils.randomGenerator.nextInt(SPHConstants.RAND_MAX) / (float)SPHConstants.RAND_MAX );
			velocityVector.setZ(MathUtils.scale(-1.0f, 1.0f, r));
			velocityVector.setP(0f);

			SPHParticle particle = factory.createSPHParticle();
			particle.setPositionVector(positionVector);
			particle.setVelocityVector(velocityVector);
			particle.setMass(1f);
			model.getParticles().add(particle);
		}
	}
	
	public static void generateElasticScene(SPHModel model){
		SPHFactory factory = new SPHFactory();
		int numOfLiquidP = 0;
		int numOfElasticP = 0;
		int numOfBoundaryP = 0;
		
		for(int stage=0; stage<2; stage++)
		{
			int PARTICLE_COUNT_ELASTIC_SCENE = 0;
			
			// ported from C++ version - owHelper::generateConfiguration
			float x,y,z;
			float p_type = SPHConstants.LIQUID_TYPE;
			int i = 0;// particle counter
			int ix,iy,iz;
			int ecc = 0;//elastic connections counter
	
			int nx = (int)( ( XMAX - XMIN ) / SPHConstants.R0 ); //X
			int ny = (int)( ( YMAX - YMIN ) / SPHConstants.R0 ); //Y
			int nz = (int)( ( ZMAX - ZMIN ) / SPHConstants.R0 ); //Z
	
			int nEx = 9;
			int nEy = 5;
			int nEz = 35;
		
			if(stage==0)
			{
				numOfLiquidP = 0;
				numOfElasticP = nEx*nEy*nEz;
				numOfBoundaryP = 0;
				
				// no need to init anything as we have a list
			}
	
			//=============== create elastic particles ==================================================
			if(stage==1)
			{
				p_type = SPHConstants.ELASTIC_TYPE;
	
				for(x=0;x<nEx;x+=1.f)
				for(y=0;y<nEy;y+=1.f)
				for(z=0;z<nEz;z+=1.f)
				{
					createParticle(model, factory, XMAX/2+x*SPHConstants.R0-nEx*SPHConstants.R0/2, 
												   YMAX/2+y*SPHConstants.R0-nEy*SPHConstants.R0/2 + YMAX*3/8, 
												   ZMAX/2+z*SPHConstants.R0-nEz*SPHConstants.R0/2, 
												   0f, 0f, 0f, p_type);
	
					i++;
				}
	
				for(int i_ec = 0; i_ec < numOfElasticP * SPHConstants.NEIGHBOR_COUNT; i_ec++)
				{
					Connection conn = new Connection();
					conn.setP1(SPHConstants.NO_PARTICLE_ID);
					conn.setDistance(0f);
					conn.setMysteryValue(0f);
					
					model.getConnections().add(conn);
				}
	
				float r2ij;
				float dx2,dy2,dz2;
	
				for(int i_ec = 0; i_ec < numOfElasticP; i_ec++)
				{
					ecc = 0;
	
					for(int j_ec = 0; j_ec < numOfElasticP; j_ec++)
					{
						if(i_ec!=j_ec)
						{
							SPHParticle p_i_ec = model.getParticles().get(i_ec);
							SPHParticle p_j_ec = model.getParticles().get(j_ec);
							
							dx2 = (p_i_ec.getPositionVector().getX() - p_j_ec.getPositionVector().getX());
							dy2 = (p_i_ec.getPositionVector().getY() - p_j_ec.getPositionVector().getY());
							dz2 = (p_i_ec.getPositionVector().getZ() - p_j_ec.getPositionVector().getZ());
							dx2 *= dx2;
							dy2 *= dy2;
							dz2 *= dz2;
							r2ij = dx2 + dy2 + dz2;
	
							if(r2ij<=SPHConstants.R0*SPHConstants.R0*3.05f)
							{
								//connect elastic particles 0 and 1
								model.getConnections().get(SPHConstants.NEIGHBOR_COUNT * i_ec + ecc).setP1(((float)j_ec) + 0.1f);
								model.getConnections().get(SPHConstants.NEIGHBOR_COUNT * i_ec + ecc).setDistance((float)Math.sqrt(r2ij)* SPHConstants.SIMULATION_SCALE);
								model.getConnections().get(SPHConstants.NEIGHBOR_COUNT * i_ec + ecc).setMysteryValue((float) (0 + 1.1 * ((((dz2>100*dx2)&&(dz2>100*dy2))) ? 1 : 0)));
								ecc++;
							}
	
							if(ecc>=SPHConstants.NEIGHBOR_COUNT) break;
						}
					}
				}
			}
	
			//============= create volume of liquid =========================================================================
			p_type = SPHConstants.LIQUID_TYPE;
	
			for(x = 15*SPHConstants.R0/2;x<(XMAX-XMIN)/5 +3*SPHConstants.R0/2;x += SPHConstants.R0)
			for(y =  3*SPHConstants.R0/2;y<(YMAX-YMIN)   -3*SPHConstants.R0/2;y += SPHConstants.R0)
			for(z =  3*SPHConstants.R0/2+(ZMAX-ZMIN)*3/10;z<(ZMAX-ZMIN)*7/10-3*SPHConstants.R0/2;z += SPHConstants.R0)
			{
				// stage==0 - preliminary run
				// stage==1 - final run
				if(stage==1)
				{
					if(i>=numOfLiquidP+numOfElasticP) 
					{
						System.out.println("Warning - final particle count >= preliminary particle count!");
						return;
					}
					
					createParticle(model, factory, x, y, z, 0f, 0f, 0f, p_type);
				}
	
				i++; // necessary for both stages
			}
			// end
	
			if(stage==0) 
			{
				numOfLiquidP = i;// - numOfElasticP;
				numOfBoundaryP = 2 * ( nx*ny + (nx+ny-2)*(nz-2) ); 
			}
			else if(stage==1)
			{
				//===================== create boundary particles ==========================================================
				p_type = SPHConstants.BOUNDARY_TYPE;
	
				// 1 - top and bottom 
				for(ix=0;ix<nx;ix++)
				{
					for(iy=0;iy<ny;iy++)
					{
						if( ((ix==0)||(ix==nx-1)) || ((iy==0)||(iy==ny-1)) )
						{
							if( ((ix==0)||(ix==nx-1)) && ((iy==0)||(iy==ny-1)) )//corners
							{
								createParticle(model, factory, ix*SPHConstants.R0 + SPHConstants.R0/2, 
															   iy*SPHConstants.R0 + SPHConstants.R0/2, 
															   0*SPHConstants.R0 + SPHConstants.R0/2, 
															   (float) (( 1.f*((ix==0)?1:0) -1 * ((ix==nx-1)?1:0) )/Math.sqrt(3.f)), 
															   (float) (( 1.f*((iy==0)?1:0) -1 * ((iy==ny-1)?1:0) )/Math.sqrt(3.f)), 
															   (float) (1.f/Math.sqrt(3.f)), p_type);
								
								i++;
								
								createParticle(model, factory, ix*SPHConstants.R0 + SPHConstants.R0/2, 
															   iy*SPHConstants.R0 + SPHConstants.R0/2, 
															   (nz-1)*SPHConstants.R0 + SPHConstants.R0/2, 
															   (float) ((1*((ix==0)?1:0) -1*((ix==nx-1)?1:0) )/Math.sqrt(3.f)), 
															   (float) ((1*((iy==0)?1:0) -1*((iy==ny-1)?1:0) )/Math.sqrt(3.f)), 
															   (float) (-1.f/Math.sqrt(3.f)), p_type);
								
								i++;
							}
							else //edges
							{
								createParticle(model, factory, ix*SPHConstants.R0 + SPHConstants.R0/2, 
															   iy*SPHConstants.R0 + SPHConstants.R0/2, 
															   0*SPHConstants.R0 + SPHConstants.R0/2, 
															   (float) (1.f*(((ix==0)?1:0) - ((ix==nx-1)?1:0))/Math.sqrt(2.f)), 
															   (float) (1.f*(((iy==0)?1:0) - ((iy==ny-1)?1:0))/Math.sqrt(2.f)), 
															   (float) (1.f/Math.sqrt(2.f)), p_type);
								
								i++;
								
								createParticle(model, factory, ix*SPHConstants.R0 + SPHConstants.R0/2, 
															   iy*SPHConstants.R0 + SPHConstants.R0/2, 
															   (nz-1)*SPHConstants.R0 + SPHConstants.R0/2, 
															   (float) (1.f*(((ix==0)?1:0) - ((ix==nx-1)?1:0))/Math.sqrt(2.f)), 
															   (float) (1.f*(((iy==0)?1:0) - ((iy==ny-1)?1:0))/Math.sqrt(2.f)), 
															   (float) (-1.f/Math.sqrt(2.f)), p_type);
								
								i++;
							}
						}
						else //planes
						{
							createParticle(model, factory, ix*SPHConstants.R0 + SPHConstants.R0/2, 
														   iy*SPHConstants.R0 + SPHConstants.R0/2, 
														   0*SPHConstants.R0 + SPHConstants.R0/2, 
														   0f, 0f, 1f, p_type);
					
							i++;
							
							createParticle(model, factory, ix*SPHConstants.R0 + SPHConstants.R0/2, 
									 					   iy*SPHConstants.R0 + SPHConstants.R0/2, 
									 					  (nz-1)*SPHConstants.R0 + SPHConstants.R0/2, 
									 					   0f, 0f, -1f, p_type);
							
							i++;
						}
					}
				}
	
				// 2 - side walls OX-OZ and opposite
				for(ix=0;ix<nx;ix++)
				{
					for(iz=1;iz<nz-1;iz++)
					{
						//edges
						if((ix==0)||(ix==nx-1))
						{
							createParticle(model, factory, ix*SPHConstants.R0 + SPHConstants.R0/2, 
														   0*SPHConstants.R0 + SPHConstants.R0/2, 
														   iz*SPHConstants.R0 + SPHConstants.R0/2, 
														   0f, 
														   (float) (1.f/Math.sqrt(2.f)), 
														   (float) (1.f*(((iz==0)?1:0) - ((iz==nz-1)?1:0))/Math.sqrt(2.f)), p_type);

							i++;
							
							createParticle(model, factory, ix*SPHConstants.R0 + SPHConstants.R0/2, 
														   (ny-1)*SPHConstants.R0 + SPHConstants.R0/2, 
														   iz*SPHConstants.R0 + SPHConstants.R0/2, 
														   0f, 
														   (float) (-1.f/Math.sqrt(2.f)), 
														   (float) (1.f*(((iz==0)?1:0) - ((iz==nz-1)?1:0))/Math.sqrt(2.f)), p_type);
							
							i++;
						}
						else //planes
						{
							createParticle(model, factory, ix*SPHConstants.R0 + SPHConstants.R0/2, 
														   0*SPHConstants.R0 + SPHConstants.R0/2, 
														   iz*SPHConstants.R0 + SPHConstants.R0/2, 
														   0f, 
														   1f, 
														   0f, p_type);
							
							i++;
							
							createParticle(model, factory, ix*SPHConstants.R0 + SPHConstants.R0/2, 
														   (ny-1)*SPHConstants.R0 + SPHConstants.R0/2, 
														   iz*SPHConstants.R0 + SPHConstants.R0/2, 
														   0f, 
														   -1f, 
														   0f, p_type);
							
							i++;
						}
					}
				}
	
				// 3 - side walls OY-OZ and opposite
				for(iy=1;iy<ny-1;iy++)
				{
					for(iz=1;iz<nz-1;iz++)
					{
						createParticle(model, factory, 0*SPHConstants.R0 + SPHConstants.R0/2, 
													   iy*SPHConstants.R0 + SPHConstants.R0/2, 
													   iz*SPHConstants.R0 + SPHConstants.R0/2, 
													   1f, 
													   0f, 
													   0f, p_type);
						
						i++;
						
						createParticle(model, factory, (nx-1)*SPHConstants.R0 + SPHConstants.R0/2, 
													   iy*SPHConstants.R0 + SPHConstants.R0/2, 
													   iz*SPHConstants.R0 + SPHConstants.R0/2, 
													   -1f, 
													   0f, 
													   0f, p_type);

						i++;
					}
				}
			}
	
			if(stage==0)
			{
				PARTICLE_COUNT_ELASTIC_SCENE = numOfLiquidP + numOfBoundaryP + numOfElasticP;
	
				if(PARTICLE_COUNT_ELASTIC_SCENE<=0) 
				{
					System.out.println("Warning! Generated scene contains " + PARTICLE_COUNT_ELASTIC_SCENE + " particles!");
					return;
				}
			}
			else if(stage==1)
			{
				if(PARTICLE_COUNT_ELASTIC_SCENE!=i) 
				{
					System.out.println("Warning! Preliminary " + PARTICLE_COUNT_ELASTIC_SCENE + " and final " + i + " particle count are different!");
					return;
				}
			}
		}

		System.out.println("Generate elastic scene - all was good!");
		return;
	}

	private static void createParticle(SPHModel model, SPHFactory factory, float p_x, float p_y, float p_z, float v_x, float v_y, float v_z, float p_type) {
		Vector3D positionVector = factory.createVector3D();
		positionVector.setX(round(p_x, 3));
		positionVector.setY(round(p_y, 3));
		positionVector.setZ(round(p_z, 3));
		positionVector.setP(p_type);

		Vector3D velocityVector = factory.createVector3D();
		velocityVector.setX(round(v_x, 6));
		velocityVector.setY(round(v_y, 6));
		velocityVector.setZ(round(v_z, 6));
		velocityVector.setP(p_type);
		
		// add particles
		SPHParticle particle = factory.createSPHParticle();
		particle.setPositionVector(positionVector);
		particle.setVelocityVector(velocityVector);
		particle.setMass(1f);
		model.getParticles().add(particle);
	}
	
	public static float round(float d, int decimalPlace) 
	{
		BigDecimal bd = new BigDecimal(Float.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {


		SPHModel model=createModel();

		// create JAXB context and instantiate marshaller
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(SPHModel.class);

			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(model, System.out);

			Writer w = null;
			try {
				w = new FileWriter(SPH_XML);
				m.marshal(model, w);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					w.close();
				} catch (Exception e) {
				}
			}

			// get variables from our xml file, created before
			System.out.println();
			System.out.println("Output from our XML File: ");
			Unmarshaller um = context.createUnmarshaller();
			SPHModel sphModel = (SPHModel) um.unmarshal(new FileReader(SPH_XML));

			for (int i = 0; i < sphModel.getParticles().toArray().length; i++) {
				System.out.println("Particle " + (i + 1) + ": type "
						+ sphModel.getParticles().get(i).getPositionVector().getP() + " mass "
						+ sphModel.getParticles().get(i).getMass() + " position "
						+ sphModel.getParticles().get(i).getPositionVector() + " velocity "
						+ sphModel.getParticles().get(i).getVelocityVector());
			}

		} catch (JAXBException e1) {
			e1.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
