package fr.shinigota.engine.graphic;

import org.joml.Matrix3f;
import org.joml.Vector3f;

/**
 * @see <a href="https://learnopengl.com/code_viewer_gh.php?code=includes/learnopengl/camera.h">https://learnopengl.com/code_viewer_gh.php?code=includes/learnopengl/camera.h</a>
 * @see <a href="https://learnopengl.com/Getting-started/Camera">https://learnopengl.com/Getting-started/Camera</a>
 * @see <a href="http://www.songho.ca/opengl/gl_anglestoaxes.html">http://www.songho.ca/opengl/gl_anglestoaxes.html</a>
 */
public class Camera {
    private final Vector3f position;
    /**
     * Euler angles
     * rotation.x = pitch
     * rotation.y = yaw
     * rotation.z = roll
     */
    private final Vector3f rotation;

    private final Vector3f front;
    private final Vector3f up;
    private /*final*/ Vector3f right;

    private final Transformation transformation;


    public Camera() {
        this(new Vector3f(0, 0, 0),  new Vector3f(0, 0, 0));
    }

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;

        front = new Vector3f(0, 0, -1);
        up = new Vector3f(0, 1, 0);
//        right = new Vector3f(1, 0, 0);

        updateCameraVectors();
        transformation = new Transformation();
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public void movePosition(float offsetX, float offsetY, float offsetZ) {

        Vector3f left = new Vector3f(offsetX, 0, 0);
        Vector3f up = new Vector3f(0, offsetY, 0);
        Vector3f forward = new Vector3f(0, 0, offsetZ);

        anglesToAxes(rotation, left, up, forward);
//
//        position.add(left).add(up).add(forward);

        if (offsetZ != 0) {
            position.x += (float) Math.sin(Math.toRadians(rotation.y)) * -1.0f * offsetZ;
            position.z += (float) Math.cos(Math.toRadians(rotation.y)) * offsetZ;
        }
        if (offsetX != 0) {
            position.x += (float) Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * offsetX;
            position.z += (float) Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;
        }
        position.y += offsetY;
//        Vector3f dir = new Vector3f(offsetX, offsetY, offsetZ);
//        dir.rotateX((float) Math.toRadians(rotation.x + 90));//.rotateY((float) Math.toRadians(rotation.y - 45));
//        position.add(dir);
    }


    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
    }

    public void moveRotation(float offsetX, float offsetY, float offsetZ) {
        rotation.x += offsetX;
        rotation.y += offsetY;
        rotation.z += offsetZ;

        updateCameraVectors();
    }

    private void updateCameraVectors() {
        
    }

    private Matrix3f anglesToAxes(final Vector3f angles, Vector3f left, Vector3f up, Vector3f forward) {
        float sx, sy, sz, cx, cy, cz, theta;

        // rotation angle about X-axis (pitch)
        theta = (float) Math.toRadians(angles.x);
        sx = (float) Math.sin(theta);
        cx = (float) Math.cos(theta);

        // rotation angle about Y-axis (yaw)
        theta = (float) Math.toRadians(angles.y);
        sy = (float) Math.sin(theta);
        cy = (float) Math.cos(theta);

        // rotation angle about Z-axis (roll)
        theta = (float) Math.toRadians(angles.z);
        sz = (float) Math.sin(theta);
        cz = (float) Math.cos(theta);

        // determine left axis
        left.x = cy*cz;
        left.y = sx*sy*cz + cx*sz;
        left.z = -cx*sy*cz + sx*sz;

        // determine up axis
        up.x = -cy*sz;
        up.y = -sx*sy*sz + cx*cz;
        up.z = cx*sy*sz + sx*cz;

        // determine forward axis
        forward.x = sy;
        forward.y = -sx*cy;
        forward.z = cx*cy;

        System.out.println("left " + left.x + "; " + left.y + "; " + left.z);
        System.out.println("up " + up.x + "; " + up.y + "; " + up.z);
        System.out.println("forward " + forward.x + "; " + forward.y + "; " + forward.z);

        return new Matrix3f(left, up, forward);
    }
}
