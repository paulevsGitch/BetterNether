#include frex:shaders/api/vertex.glsl
#include frex:shaders/api/world.glsl
#include frex:shaders/lib/math.glsl
#include frex:shaders/lib/noise/noise4d.glsl

/******************************************************
  Based on "GPU-Generated Procedural Wind Animations for Trees"
  by Renaldas Zioma in GPU Gems 3, 2007
  https://developer.nvidia.com/gpugems/gpugems3/part-i-geometry/chapter-6-gpu-generated-procedural-wind-animations-trees
  
  Remake of canvas default shader for the Nether
******************************************************/

#define NOISE_SCALE 0.125

void frx_startVertex(inout frx_VertexData data) {
	data.vertex.xz += data.normal.xz * data.spriteUV.y * 0.02;
}
