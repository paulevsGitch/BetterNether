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

void frx_startVertex(inout frx_VertexData data) {
	#ifdef ANIMATED_FOLIAGE
		float t = frx_renderSeconds() * 0.05;

		vec3 pos = (data.vertex.xyz + frx_modelOriginWorldPos()) * 0.5;
		float wind = snoise(vec4(pos, t)) * 0.1;
		
		//vec3 center = frx_modelOriginWorldPos() + vec3(0.5);
		//vec3 absDist = abs(pos - center) * 2;
		//wind *= clamp(1 - absDist.x + absDist.y + absDist.z, 0, 1);
		wind *= 1 - data.spriteUV.y;

		data.vertex.y += (cos(t) * cos(t * 3) * cos(t * 5) * cos(t * 7) + sin(t * 25)) * wind;
		data.vertex.x += cos(t * 14) * wind;
		data.vertex.z += sin(t * 19) * wind;
	#endif
}
