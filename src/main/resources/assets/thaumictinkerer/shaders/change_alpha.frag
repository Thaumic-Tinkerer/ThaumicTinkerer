#version 120

uniform float alpha;
uniform sampler2D tex;

void main() {
    gl_FragColor = texture2D(tex, vec2(gl_TexCoord[0])) * gl_Color * vec4(1.0, 1.0, 1.0, alpha);
}