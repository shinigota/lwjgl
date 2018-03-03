package fr.shinigota.engine.graphic;

import org.joml.Matrix3f;
import org.joml.Vector3f;

/**
 * @see <a href="https://learnopengl.com/code_viewer_gh.php?code=includes/learnopengl/camera.h">https://learnopengl.com/code_viewer_gh.php?code=includes/learnopengl/camera.h</a>
 * @see <a href="https://learnopengl.com/Getting-started/Camera">https://learnopengl.com/Getting-started/Camera</a>
 * @see <a href="http://www.songho.ca/opengl/gl_anglestoaxes.html">http://www.songho.ca/opengl/gl_anglestoaxes.html</a>
 */
public class Camera {
    public enum Direction {
        FORWARD,
        BACKWARD,
        RIGHT,
        LEFT
    }
    private final Vector3f position;
    /**
     * Euler angles
     * rotation.x = pitch
     * rotation.y = yaw
     * rotation.z = roll
     */
    private final Vector3f rotation;

    private Vector3f front;
    private Vector3f up;
    private Vector3f right;

    public Camera() {
        this(new Vector3f(0, 0, 0),  new Vector3f(0, 0, 0));
    }

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;

        front = new Vector3f(0, 0, -1);
        up = new Vector3f(0, 1, 0);

        updateCameraVectors();
    }

    public Vector3f getPosition() {
        return position;
    }


    public Vector3f getRotation() {
        return rotation;
    }


    public void moveRotation(float offsetX, float offsetY) {
        rotation.x += offsetX;
        rotation.y += offsetY;

        if (rotation.x > 89.0f) {
            rotation.x = 89.0f;
        }
        if (rotation.x < -89.0f) {
            rotation.x = -89.0f;
        }

        updateCameraVectors();
    }

    private void updateCameraVectors() {
        Vector3f tmpFront = new Vector3f();
        tmpFront.x = (float) (Math.cos(Math.toRadians(rotation.y - 90)) * Math.cos(Math.toRadians(-rotation.x)));
        tmpFront.y = (float) Math.sin(Math.toRadians(-rotation.x));
        tmpFront.z = (float) (Math.sin(Math.toRadians(rotation.y - 90)) * Math.cos(Math.toRadians(-rotation.x)));
        front = tmpFront.normalize();

        Vector3f tmpRight = new Vector3f();
        tmpRight.x = (float) (Math.cos(Math.toRadians(rotation.y)) * Math.cos(Math.toRadians(-rotation.x)));
        tmpRight.y = 0;
        tmpRight.z = (float) (Math.sin(Math.toRadians(rotation.y)) * Math.cos(Math.toRadians(-rotation.x)));
        right = tmpRight.normalize();

    }

    public void moveToDirection(Direction direction) {
        float velocity = 0.2f;
        if(direction == Direction.FORWARD) {
            position.add(new Vector3f(front).mul(velocity));
        }
        if(direction == Direction.BACKWARD) {
            position.sub(new Vector3f(front).mul(velocity));
        }
        if(direction == Direction.LEFT) {
            position.sub(new Vector3f(right).mul(velocity));
        }
        if(direction == Direction.RIGHT) {
            position.add(new Vector3f(right).mul(velocity));
        }
    }
}
