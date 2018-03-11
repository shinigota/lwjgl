#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;
layout (location=2) in mat4 modelViewInstancedMatrix;

out vec2 outTexCoord;

uniform int isInstanced;
uniform mat4 projectionMatrix;
uniform mat4 modelWorldNonInstancedMatrix;

void main()
{
    mat4 modelWorldMatrix;

    if ( isInstanced > 0 )
    {
        modelWorldMatrix = modelViewInstancedMatrix;
    }
    else
    {
        modelWorldMatrix = modelWorldNonInstancedMatrix;
    }

    gl_Position = projectionMatrix * modelWorldMatrix * vec4(position, 1.0);
    outTexCoord = texCoord;
}
