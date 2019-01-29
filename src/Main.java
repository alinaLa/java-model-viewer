import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Main
{

    private Display displayXY;
    private Display displayXZ;
    private Display displayYZ;
    private Display displayP;
    JFrame frame;
    private JPanel displayT;
    private Bitmap bitmapXY;
    private Bitmap bitmapXZ;
    private Bitmap bitmapYZ;
    private Bitmap bitmapP;

    private Renderer rendererXY;
    private Renderer rendererXZ;
    private Renderer rendererYZ;
    private Renderer rendererP;

    private Loader loader;

    private Model model;

    private Vector3f rotation;
    private float scale;
    private float rotSpeed;
    private float sclFactor;

    public static Vector3f cam;
    public static Vector3f to;
    public static Vector3f light;

    public MouseListenerXY mouseXY;
    public MouseListenerXZ mouseXZ;
    public MouseListenerYZ mouseYZ;

    public static ArrayList<Vector3f> pyramidViewXY = new ArrayList<>();
    public static ArrayList<Vector3f> pyramidViewXZ = new ArrayList<>();
    public static ArrayList<Vector3f> pyramidViewYZ = new ArrayList<>();

    JButton cameraChangeButton;
    JTextField xCameraTF;
    JTextField yCameraTF;
    JTextField zCameraTF;

    JButton cameraToChangeButton;
    JTextField xCameraToTF;
    JTextField yCameraToTF;
    JTextField zCameraToTF;

    JButton lightChangeButton;
    JTextField xLightTF;
    JTextField yLightTF;
    JTextField zLightTF;

    JSlider angleSlider;
    static float angleValue = 90;

    JButton loadButton;
    JButton saveButton;

    static String modelName;

    public Main()
    {
        displayXY = new Display(750, 480, "XY");
        displayXY.getFrame().setLocation(0,0);
        displayXZ = new Display(750, 480, "XZ");
        displayXZ.getFrame().setLocation(displayXY.getFrame().getX() + displayXY.getFrame().getWidth(), displayXY.getFrame().getY());
        displayYZ = new Display(750, 480, "YZ");
        displayYZ.getFrame().setLocation(displayXY.getFrame().getX(), displayXY.getFrame().getY() + displayXY.getFrame().getHeight());
        displayP = new Display(750, 480, "Perspective");
        displayP.getFrame().setLocation(displayXY.getFrame().getX() + displayXY.getFrame().getWidth(), displayXY.getFrame().getY() + displayXY.getFrame().getHeight());
        frame = new JFrame();
        displayT = new JPanel(new GridLayout(20, 1));
        frame.setSize(420, 1030);
        frame.setLocation(displayP.getFrame().getX() + displayP.getFrame().getWidth(), 0);
        frame.getContentPane().add(displayT);
        displayT.setBackground(Color.DARK_GRAY);
        bitmapXY = displayXY.getBitmap();
        bitmapXZ = displayXZ.getBitmap();
        bitmapYZ = displayYZ.getBitmap();
        bitmapP = displayP.getBitmap();

        int c0 = 200;
        int c1 = 200;
        int c2 = 700;
        cam = new Vector3f(c0, c1, c2);
        int t0 = 50;
        int t1 = 150;
        int t2 = 50;
        to = new Vector3f(t0, t1, t2);
        int l0 = 100;
        int l1 = 100;
        int l2 = 300;
        light = new Vector3f(l0, l1, l2);

        rotation = new Vector3f();
        scale = 1.0f;
        rotSpeed = 100.0f;
        sclFactor = 1.0f;

        rendererXY = new Renderer(bitmapXY);
        rendererXY.setRenderMode(RenderMode.WIREFRAME);
        rendererXY.setRenderType(RenderType.XY);
        rendererXZ = new Renderer(bitmapXZ);
        rendererXZ.setRenderMode(RenderMode.WIREFRAME);
        rendererXZ.setRenderType(RenderType.XZ);
        rendererYZ = new Renderer(bitmapYZ);
        rendererYZ.setRenderMode(RenderMode.WIREFRAME);
        rendererYZ.setRenderType(RenderType.YZ);
        rendererP = new Renderer(bitmapP);
        rendererP.setRenderMode(RenderMode.WIREFRAME);
        rendererP.setRenderType(RenderType.P);
        updateTransformMatrix();

        loader = new Loader();
        model = loader.loadModelFromObj("monkey.obj");
        modelName = "monkey.obj";

        mouseXY = new MouseListenerXY();
        mouseXZ = new MouseListenerXZ();
        mouseYZ = new MouseListenerYZ();

        displayXY.addMouseListener(mouseXY);
        displayXY.addMouseMotionListener(mouseXY);
        displayXZ.addMouseListener(mouseXZ);
        displayXZ.addMouseMotionListener(mouseXZ);
        displayYZ.addMouseListener(mouseYZ);
        displayYZ.addMouseMotionListener(mouseYZ);

        xCameraTF = new JTextField(Integer.toString((int)cam.getX()));
        yCameraTF = new JTextField(Integer.toString((int)cam.getY()));
        zCameraTF = new JTextField(Integer.toString((int)cam.getZ()));

        cameraChangeButton = new JButton("Change camera - from");

        displayT.add(new JLabel());

        displayT.add(xCameraTF);
        displayT.add(yCameraTF);
        displayT.add(zCameraTF);

        displayT.add(cameraChangeButton);

        cameraChangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cam.setX(Integer.parseInt(xCameraTF.getText()));
                cam.setY(Integer.parseInt(yCameraTF.getText()));
                cam.setZ(Integer.parseInt(zCameraTF.getText()));
                updateTransformMatrix();

            }
        });

        displayT.add(new JLabel());

        xCameraToTF = new JTextField(Integer.toString((int)to.getX()));
        yCameraToTF = new JTextField(Integer.toString((int)to.getY()));
        zCameraToTF = new JTextField(Integer.toString((int)to.getZ()));

        cameraToChangeButton = new JButton("Change camera - to");

        displayT.add(xCameraToTF);
        displayT.add(yCameraToTF);
        displayT.add(zCameraToTF);

        displayT.add(cameraToChangeButton);

        cameraToChangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                to.setX(Integer.parseInt(xCameraToTF.getText()));
                to.setY(Integer.parseInt(yCameraToTF.getText()));
                to.setZ(Integer.parseInt(zCameraToTF.getText()));
                updateTransformMatrix();

            }
        });

        displayT.add(new JLabel());

        xLightTF = new JTextField(Integer.toString((int)light.getX()));
        yLightTF = new JTextField(Integer.toString((int)light.getY()));
        zLightTF = new JTextField(Integer.toString((int)light.getZ()));

        lightChangeButton = new JButton("Change light");

        displayT.add(xLightTF);
        displayT.add(yLightTF);
        displayT.add(zLightTF);

        displayT.add(lightChangeButton);

        lightChangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                light.setX(Integer.parseInt(xLightTF.getText()));
                light.setY(Integer.parseInt(yLightTF.getText()));
                light.setZ(Integer.parseInt(zLightTF.getText()));
                updateTransformMatrix();

            }
        });



        displayT.add(new JLabel());
        angleSlider = new JSlider(JSlider.HORIZONTAL, 1, 90, (int)angleValue);
        angleSlider.setMajorTickSpacing(5);
        angleSlider.setMinorTickSpacing(1);
        angleSlider.setPaintTicks(true);
        angleSlider.setPaintLabels(true);
        angleSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                angleValue = angleSlider.getValue();
                updateTransformMatrix();
            }
        });
        displayT.add(angleSlider);

        displayT.add(new JLabel());
        loadButton = new JButton("LOAD");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fd = new FileDialog(frame, "Choose a file", FileDialog.LOAD);
                fd.setDirectory("C:\\Users\\Alina\\IdeaProjects\\3DModelsViewer\\res");
                fd.setVisible(true);
                String filename = fd.getFile();
                if (filename == null)
                    System.out.println("You cancelled the choice");
                else{
                    System.out.println("You chose " + filename);
                    model = loader.loadModelFromObj(filename);
                }
            }
        });

        displayT.add(loadButton);


        saveButton = new JButton("SAVE");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text1 = cam.getX() + " " + cam.getY() + " " +  cam.getZ();
                String text2 = to.getX() + " " +  to.getY() + " " +  to.getZ();
                String text3 = Float.toString(angleValue);
                try (PrintWriter out = new PrintWriter("res/"+modelName.substring(0, modelName.length()-4)+".txt")) {
                    out.println(text1);
                    out.println(text2);
                    out.println(text3);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

        displayT.add(saveButton);


        frame.setVisible(true);

        long past = System.nanoTime();
        while (true)
        {
            long now = System.nanoTime();
            float dt = (float)(now - past) / 1000000000.0f;
            past = now;

            update(dt);
            render();

            displayXY.swapBuffers();
            displayXZ.swapBuffers();
            displayYZ.swapBuffers();
            displayP.swapBuffers();
        }

    }

    private void update(float dt)
    {
        if (Keyboard.isKeyDown(Keyboard.KEY_1) && Keyboard.isKeyDown(Keyboard.KEY_SHIFT)) {
            rendererXY.setRenderMode(RenderMode.WIREFRAME);
            rendererXZ.setRenderMode(RenderMode.WIREFRAME);
            rendererYZ.setRenderMode(RenderMode.WIREFRAME);
            rendererP.setRenderMode(RenderMode.WIREFRAME);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_2) && Keyboard.isKeyDown(Keyboard.KEY_SHIFT)) {
            rendererXY.setRenderMode(RenderMode.SHADED);
            rendererXZ.setRenderMode(RenderMode.SHADED);
            rendererYZ.setRenderMode(RenderMode.SHADED);
            rendererP.setRenderMode(RenderMode.SHADED);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_3) && Keyboard.isKeyDown(Keyboard.KEY_SHIFT)) {
            rendererXY.setRenderMode(RenderMode.SHADED_GOURAUD);
            rendererXZ.setRenderMode(RenderMode.SHADED_GOURAUD);
            rendererYZ.setRenderMode(RenderMode.SHADED_GOURAUD);
            rendererP.setRenderMode(RenderMode.SHADED_GOURAUD);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_4) && Keyboard.isKeyDown(Keyboard.KEY_SHIFT)) {
            rendererXY.setRenderMode(RenderMode.SHADED_PHONG);
            rendererXZ.setRenderMode(RenderMode.SHADED_PHONG);
            rendererYZ.setRenderMode(RenderMode.SHADED_PHONG);
            rendererP.setRenderMode(RenderMode.SHADED_PHONG);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_UP) && Keyboard.isKeyDown(Keyboard.KEY_SHIFT))
        {
            rotation.setX(rotation.getX() + rotSpeed * dt);
            if (rotation.getX() > 360) rotation.setX(rotation.getX() - 360);
            updateTransformMatrix();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) && Keyboard.isKeyDown(Keyboard.KEY_SHIFT))
        {
            rotation.setY(rotation.getY() - rotSpeed * dt);
            if (rotation.getY() > 360) rotation.setY(rotation.getY() - 360);
            updateTransformMatrix();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) && Keyboard.isKeyDown(Keyboard.KEY_SHIFT))
        {
            rotation.setX(rotation.getX() - rotSpeed * dt);
            if (rotation.getX() < 0) rotation.setX(rotation.getX() + 360);
            updateTransformMatrix();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && Keyboard.isKeyDown(Keyboard.KEY_SHIFT))
        {
            rotation.setY(rotation.getY() + rotSpeed * dt);
            if (rotation.getY() < 0) rotation.setY(rotation.getY() + 360);
            updateTransformMatrix();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_EQUALS) && Keyboard.isKeyDown(Keyboard.KEY_SHIFT))
        {
            scale += (scale * sclFactor) * dt;
            updateTransformMatrix();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_MINUS) && Keyboard.isKeyDown(Keyboard.KEY_SHIFT))
        {
            scale -= (scale * sclFactor) * dt;
            if (scale < .01f) scale = .01f;
            updateTransformMatrix();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Q) && Keyboard.isKeyDown(Keyboard.KEY_SHIFT))
        {
            cam.setX(cam.getX()-1);
            updateTransformMatrix();
            xCameraTF.setText(Integer.toString((int)cam.getX()));
            yCameraTF.setText(Integer.toString((int)cam.getY()));
            zCameraTF.setText(Integer.toString((int)cam.getZ()));

            xCameraToTF.setText(Integer.toString((int)to.getX()));
            yCameraToTF.setText(Integer.toString((int)to.getY()));
            zCameraToTF.setText(Integer.toString((int)to.getZ()));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W) && Keyboard.isKeyDown(Keyboard.KEY_SHIFT))
        {
            cam.setX(cam.getX()+1);
            updateTransformMatrix();
            xCameraTF.setText(Integer.toString((int)cam.getX()));
            yCameraTF.setText(Integer.toString((int)cam.getY()));
            zCameraTF.setText(Integer.toString((int)cam.getZ()));

            xCameraToTF.setText(Integer.toString((int)to.getX()));
            yCameraToTF.setText(Integer.toString((int)to.getY()));
            zCameraToTF.setText(Integer.toString((int)to.getZ()));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A) && Keyboard.isKeyDown(Keyboard.KEY_SHIFT))
        {
            cam.setY(cam.getY()-1);
            updateTransformMatrix();
            xCameraTF.setText(Integer.toString((int)cam.getX()));
            yCameraTF.setText(Integer.toString((int)cam.getY()));
            zCameraTF.setText(Integer.toString((int)cam.getZ()));

            xCameraToTF.setText(Integer.toString((int)to.getX()));
            yCameraToTF.setText(Integer.toString((int)to.getY()));
            zCameraToTF.setText(Integer.toString((int)to.getZ()));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S) && Keyboard.isKeyDown(Keyboard.KEY_SHIFT))
        {
            cam.setY(cam.getY()+1);
            updateTransformMatrix();
            xCameraTF.setText(Integer.toString((int)cam.getX()));
            yCameraTF.setText(Integer.toString((int)cam.getY()));
            zCameraTF.setText(Integer.toString((int)cam.getZ()));

            xCameraToTF.setText(Integer.toString((int)to.getX()));
            yCameraToTF.setText(Integer.toString((int)to.getY()));
            zCameraToTF.setText(Integer.toString((int)to.getZ()));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Z) && Keyboard.isKeyDown(Keyboard.KEY_SHIFT))
        {
            cam.setZ(cam.getZ()-1);
            updateTransformMatrix();
            xCameraTF.setText(Integer.toString((int)cam.getX()));
            yCameraTF.setText(Integer.toString((int)cam.getY()));
            zCameraTF.setText(Integer.toString((int)cam.getZ()));

            xCameraToTF.setText(Integer.toString((int)to.getX()));
            yCameraToTF.setText(Integer.toString((int)to.getY()));
            zCameraToTF.setText(Integer.toString((int)to.getZ()));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_X) && Keyboard.isKeyDown(Keyboard.KEY_SHIFT))
        {
            cam.setZ(cam.getZ()+1);
            updateTransformMatrix();
            xCameraTF.setText(Integer.toString((int)cam.getX()));
            yCameraTF.setText(Integer.toString((int)cam.getY()));
            zCameraTF.setText(Integer.toString((int)cam.getZ()));

            xCameraToTF.setText(Integer.toString((int)to.getX()));
            yCameraToTF.setText(Integer.toString((int)to.getY()));
            zCameraToTF.setText(Integer.toString((int)to.getZ()));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_E) && Keyboard.isKeyDown(Keyboard.KEY_SHIFT))
        {
            to.setX(to.getX()-1);
            updateTransformMatrix();
            xCameraTF.setText(Integer.toString((int)cam.getX()));
            yCameraTF.setText(Integer.toString((int)cam.getY()));
            zCameraTF.setText(Integer.toString((int)cam.getZ()));

            xCameraToTF.setText(Integer.toString((int)to.getX()));
            yCameraToTF.setText(Integer.toString((int)to.getY()));
            zCameraToTF.setText(Integer.toString((int)to.getZ()));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_R) && Keyboard.isKeyDown(Keyboard.KEY_SHIFT))
        {
            to.setX(to.getX()+1);
            updateTransformMatrix();
            xCameraTF.setText(Integer.toString((int)cam.getX()));
            yCameraTF.setText(Integer.toString((int)cam.getY()));
            zCameraTF.setText(Integer.toString((int)cam.getZ()));

            xCameraToTF.setText(Integer.toString((int)to.getX()));
            yCameraToTF.setText(Integer.toString((int)to.getY()));
            zCameraToTF.setText(Integer.toString((int)to.getZ()));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D) && Keyboard.isKeyDown(Keyboard.KEY_SHIFT))
        {
            to.setY(to.getY()-1);
            updateTransformMatrix();
            xCameraTF.setText(Integer.toString((int)cam.getX()));
            yCameraTF.setText(Integer.toString((int)cam.getY()));
            zCameraTF.setText(Integer.toString((int)cam.getZ()));

            xCameraToTF.setText(Integer.toString((int)to.getX()));
            yCameraToTF.setText(Integer.toString((int)to.getY()));
            zCameraToTF.setText(Integer.toString((int)to.getZ()));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_F) && Keyboard.isKeyDown(Keyboard.KEY_SHIFT))
        {
            to.setY(to.getY()+1);
            updateTransformMatrix();
            xCameraTF.setText(Integer.toString((int)cam.getX()));
            yCameraTF.setText(Integer.toString((int)cam.getY()));
            zCameraTF.setText(Integer.toString((int)cam.getZ()));

            xCameraToTF.setText(Integer.toString((int)to.getX()));
            yCameraToTF.setText(Integer.toString((int)to.getY()));
            zCameraToTF.setText(Integer.toString((int)to.getZ()));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_C) && Keyboard.isKeyDown(Keyboard.KEY_SHIFT))
        {
            to.setZ(to.getZ()-1);
            updateTransformMatrix();
            xCameraTF.setText(Integer.toString((int)cam.getX()));
            yCameraTF.setText(Integer.toString((int)cam.getY()));
            zCameraTF.setText(Integer.toString((int)cam.getZ()));

            xCameraToTF.setText(Integer.toString((int)to.getX()));
            yCameraToTF.setText(Integer.toString((int)to.getY()));
            zCameraToTF.setText(Integer.toString((int)to.getZ()));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_V) && Keyboard.isKeyDown(Keyboard.KEY_SHIFT))
        {
            to.setZ(to.getZ()+1);
            updateTransformMatrix();
            xCameraTF.setText(Integer.toString((int)cam.getX()));
            yCameraTF.setText(Integer.toString((int)cam.getY()));
            zCameraTF.setText(Integer.toString((int)cam.getZ()));

            xCameraToTF.setText(Integer.toString((int)to.getX()));
            yCameraToTF.setText(Integer.toString((int)to.getY()));
            zCameraToTF.setText(Integer.toString((int)to.getZ()));
        }
        //updateTransformMatrix();
    }

    private void render()
    {
        bitmapXY.clear(0x000000);
        bitmapXZ.clear(0x000000);
        bitmapYZ.clear(0x000000);
        bitmapP.clear(0x000000);
        rendererXY.render(model);
        rendererXZ.render(model);
        rendererYZ.render(model);
        rendererP.render(model);
        displayXY.swapBuffers();
        displayXZ.swapBuffers();
        displayYZ.swapBuffers();
        displayP.swapBuffers();
    }

    private void updateTransformMatrix()
    {
        Matrix4f tXY = new Matrix4f().initTranslation(bitmapXY.getWidth()/2, bitmapXY.getHeight()/2, 0);
        Matrix4f tXZ = new Matrix4f().initTranslation(bitmapXZ.getWidth()/2, 0, bitmapXZ.getHeight()/2);
        Matrix4f tYZ = new Matrix4f().initTranslation(0, bitmapYZ.getWidth()/2, bitmapYZ.getHeight()/2);
        Matrix4f r = new Matrix4f().initRotation(rotation.getX(), rotation.getY(), rotation.getZ());
        Matrix4f s = new Matrix4f().initScale(scale, scale, scale);
        rendererXY.setTransform(tXY.mul(r).mul(s));
        rendererXZ.setTransform(tXZ.mul(r).mul(s));
        rendererYZ.setTransform(tYZ.mul(r).mul(s));
        Matrix4f camToWorld = lookAt(cam, to);
        //System.out.println(angleValue/90);
        rendererP.setTransform(camToWorld.mul(tXY).mul(r).mul(new Matrix4f().initScale(90/angleValue, 90/angleValue, 90/angleValue)));
        Matrix4f transform0 = tXY.mul(s);
        rendererXY.setCam(new Vector3f(transform0.transform(new Vector4f(cam, 1))));
        rendererXY.setTo(new Vector3f(transform0.transform(new Vector4f(to, 1))));
        rendererXY.setLight(new Vector3f(transform0.transform(new Vector4f(light, 1))));
        Matrix4f transform1 = tXZ.mul(s);
        rendererXZ.setCam(new Vector3f(transform1.transform(new Vector4f(cam, 1))));
        rendererXZ.setTo(new Vector3f(transform1.transform(new Vector4f(to, 1))));
        rendererXZ.setLight(new Vector3f(transform1.transform(new Vector4f(light, 1))));
        Matrix4f transform2 = tYZ.mul(s);
        rendererYZ.setCam(new Vector3f(transform2.transform(new Vector4f(cam, 1))));
        rendererYZ.setTo(new Vector3f(transform2.transform(new Vector4f(to, 1))));
        rendererYZ.setLight(new Vector3f(transform2.transform(new Vector4f(light, 1))));

        Vector4f p40 = new Vector4f(new Vector3f(camToWorld.mul(new Matrix4f().initScale(90/angleValue, 90/angleValue, 90/angleValue)).wyznaczMacierzOdwrotna().transform(new Vector4f(new Vector3f(0, 0, 0), 1))), 1);
        Vector4f p41 = new Vector4f(new Vector3f(camToWorld.mul(new Matrix4f().initScale(90/angleValue, 90/angleValue, 90/angleValue)).wyznaczMacierzOdwrotna().transform(new Vector4f(new Vector3f(bitmapP.getWidth(), 0, 0), 1))), 1);
        Vector4f p42 = new Vector4f(new Vector3f(camToWorld.mul(new Matrix4f().initScale(90/angleValue, 90/angleValue, 90/angleValue)).wyznaczMacierzOdwrotna().transform(new Vector4f(new Vector3f(bitmapP.getWidth(), bitmapP.getHeight(), 0), 1))), 1);
        Vector4f p43 = new Vector4f(new Vector3f(camToWorld.mul(new Matrix4f().initScale(90/angleValue, 90/angleValue, 90/angleValue)).wyznaczMacierzOdwrotna().transform(new Vector4f(new Vector3f(0, bitmapP.getHeight(), 0), 1))), 1);

        transform0 = new Matrix4f().initTranslation(rendererXY.getTo().getX(), rendererXY.getTo().getY(), 0).mul(s);
        Vector3f p0 = new Vector3f(transform0.transform(p40));
        Vector3f p1 = new Vector3f(transform0.transform(p41));
        Vector3f p2 = new Vector3f(transform0.transform(p42));
        Vector3f p3 = new Vector3f(transform0.transform(p43));
        int xDiff = (int)Math.max(Math.abs(p0.getX()-p1.getX()), Math.abs(p0.getX()-p2.getX()));
        int yDiff = (int)Math.max(Math.abs(p0.getY()-p1.getY()), Math.abs(p0.getY()-p2.getY()));
        transform0 = new Matrix4f().initTranslation(rendererXY.getTo().getX()-xDiff/2, rendererXY.getTo().getY()-yDiff/2, 0).mul(s);
        p0 = new Vector3f(transform0.transform(p40));
        p1 = new Vector3f(transform0.transform(p41));
        p2 = new Vector3f(transform0.transform(p42));
        p3 = new Vector3f(transform0.transform(p43));

        if(!pyramidViewXY.isEmpty()) {
            pyramidViewXY.set(0, p0);
            pyramidViewXY.set(1, p1);
            pyramidViewXY.set(2, p2);
            pyramidViewXY.set(3, p3);
        }else{
            pyramidViewXY.add(p0);
            pyramidViewXY.add(p1);
            pyramidViewXY.add(p2);
            pyramidViewXY.add(p3);
        }

        p40 = new Vector4f(new Vector3f(camToWorld.mul(new Matrix4f().initScale(90/angleValue, 90/angleValue, 90/angleValue)).wyznaczMacierzOdwrotna().transform(new Vector4f(new Vector3f(0, 0, 0), 1))), 1);
        p41 = new Vector4f(new Vector3f(camToWorld.mul(new Matrix4f().initScale(90/angleValue, 90/angleValue, 90/angleValue)).wyznaczMacierzOdwrotna().transform(new Vector4f(new Vector3f(bitmapP.getWidth(), 0, 0), 1))), 1);
        p42 = new Vector4f(new Vector3f(camToWorld.mul(new Matrix4f().initScale(90/angleValue, 90/angleValue, 90/angleValue)).wyznaczMacierzOdwrotna().transform(new Vector4f(new Vector3f(bitmapP.getWidth(), bitmapP.getHeight(), 0), 1))), 1);
        p43 = new Vector4f(new Vector3f(camToWorld.mul(new Matrix4f().initScale(90/angleValue, 90/angleValue, 90/angleValue)).wyznaczMacierzOdwrotna().transform(new Vector4f(new Vector3f(0, bitmapP.getHeight(), 0), 1))), 1);
        transform1 = new Matrix4f().initTranslation(rendererXZ.getTo().getX(), 0, rendererXZ.getTo().getZ()).mul(s);
        p0 = new Vector3f(transform1.transform(p40));
        p1 = new Vector3f(transform1.transform(p41));
        p2 = new Vector3f(transform1.transform(p42));
        p3 = new Vector3f(transform1.transform(p43));
        xDiff = (int)Math.max(Math.abs(p0.getX()-p1.getX()), Math.abs(p0.getX()-p2.getX()));
        yDiff = (int)Math.max(Math.abs(p0.getZ()-p1.getZ()), Math.abs(p0.getZ()-p2.getZ()));
        transform1 = new Matrix4f().initTranslation(rendererXZ.getTo().getX()-xDiff/2, 0, rendererXZ.getTo().getZ()-yDiff/2).mul(s);
        p0 = new Vector3f(transform1.transform(p40));
        p1 = new Vector3f(transform1.transform(p41));
        p2 = new Vector3f(transform1.transform(p42));
        p3 = new Vector3f(transform1.transform(p43));
        if(!pyramidViewXZ.isEmpty()) {
            pyramidViewXZ.set(0, p0);
            pyramidViewXZ.set(1, p1);
            pyramidViewXZ.set(2, p2);
            pyramidViewXZ.set(3, p3);
        }else{
            pyramidViewXZ.add(p0);
            pyramidViewXZ.add(p1);
            pyramidViewXZ.add(p2);
            pyramidViewXZ.add(p3);
        }

        p40 = new Vector4f(new Vector3f(camToWorld.mul(new Matrix4f().initScale(90/angleValue, 90/angleValue, 90/angleValue)).wyznaczMacierzOdwrotna().transform(new Vector4f(new Vector3f(0, 0, 0), 1))), 1);
        p41 = new Vector4f(new Vector3f(camToWorld.mul(new Matrix4f().initScale(90/angleValue, 90/angleValue, 90/angleValue)).wyznaczMacierzOdwrotna().transform(new Vector4f(new Vector3f(bitmapP.getWidth(), 0, 0), 1))), 1);
        p42 = new Vector4f(new Vector3f(camToWorld.mul(new Matrix4f().initScale(90/angleValue, 90/angleValue, 90/angleValue)).wyznaczMacierzOdwrotna().transform(new Vector4f(new Vector3f(bitmapP.getWidth(), bitmapP.getHeight(), 0), 1))), 1);
        p43 = new Vector4f(new Vector3f(camToWorld.mul(new Matrix4f().initScale(90/angleValue, 90/angleValue, 90/angleValue)).wyznaczMacierzOdwrotna().transform(new Vector4f(new Vector3f(0, bitmapP.getHeight(), 0), 1))), 1);
        transform2 = new Matrix4f().initTranslation(0, rendererYZ.getTo().getY(), rendererYZ.getTo().getZ()).mul(s);
        p0 = new Vector3f(transform2.transform(p40));
        p1 = new Vector3f(transform2.transform(p41));
        p2 = new Vector3f(transform2.transform(p42));
        p3 = new Vector3f(transform2.transform(p43));
        xDiff = (int)Math.max(Math.abs(p0.getY()-p1.getY()), Math.abs(p0.getY()-p2.getY()));
        yDiff = (int)Math.max(Math.abs(p0.getZ()-p1.getZ()), Math.abs(p0.getZ()-p2.getZ()));
        transform2 = new Matrix4f().initTranslation(0, rendererYZ.getTo().getY()-xDiff/2, rendererYZ.getTo().getZ()-yDiff/2).mul(s);
        p0 = new Vector3f(transform2.transform(p40));
        p1 = new Vector3f(transform2.transform(p41));
        p2 = new Vector3f(transform2.transform(p42));
        p3 = new Vector3f(transform2.transform(p43));
        if(!pyramidViewYZ.isEmpty()) {
            pyramidViewYZ.set(0, p0);
            pyramidViewYZ.set(1, p1);
            pyramidViewYZ.set(2, p2);
            pyramidViewYZ.set(3, p3);
        }else{
            pyramidViewYZ.add(p0);
            pyramidViewYZ.add(p1);
            pyramidViewYZ.add(p2);
            pyramidViewYZ.add(p3);
        }
    }


    public Matrix4f lookAt(Vector3f from, Vector3f to)
    {

        Vector3f tmp = new Vector3f(0, 1, 0);
        Vector3f forward = (from.sub(to)).normalized();
        Vector3f right = (tmp.normalized()).cross(forward);
        Vector3f up = forward.cross(right);

        Matrix4f camToWorld = new Matrix4f();

        camToWorld.set(0, 0, right.getX());
        camToWorld.set(0, 1, right.getY());
        camToWorld.set(0, 2, right.getZ());
        camToWorld.set(1, 0, up.getX());
        camToWorld.set(1, 1, up.getY());
        camToWorld.set(1, 2, up.getZ());
        camToWorld.set(2, 0, forward.getX());
        camToWorld.set(2, 1, forward.getY());
        camToWorld.set(2, 2, forward.getZ());

        camToWorld.set(3, 0, from.getX());
        camToWorld.set(3, 1, from.getY());
        camToWorld.set(3, 2, from.getZ());

        camToWorld.set(0, 3, 0);
        camToWorld.set(1, 3, 0);
        camToWorld.set(2, 3, 0);
        camToWorld.set(3, 3, 1);


        return camToWorld;
   }

    public static void main(String[] args)
    {
        new Main();
    }

    public class MouseListenerXY implements MouseListener, MouseMotionListener
    {
        Matrix4f tXY = new Matrix4f().initTranslation(bitmapXY.getWidth()/2, bitmapXY.getHeight()/2, 0);
        Matrix4f s = new Matrix4f().initScale(scale, scale, scale);
        Matrix4f transform0 = tXY.mul(s);
        int x = (int)(new Vector3f(transform0.transform(new Vector4f(cam, 1))).getX());
        int y = (int)(new Vector3f(transform0.transform(new Vector4f(cam, 1))).getY());
        int x2 = (int)(new Vector3f(transform0.transform(new Vector4f(to, 1))).getX());
        int y2 = (int)(new Vector3f(transform0.transform(new Vector4f(to, 1))).getY());
        int x3 = (int)(new Vector3f(transform0.transform(new Vector4f(light, 1))).getX());
        int y3 = (int)(new Vector3f(transform0.transform(new Vector4f(light, 1))).getY());

        public void mouseDragged(MouseEvent e)
        {
            if(Math.abs(x-e.getX())<10 && Math.abs(y-e.getY())<10) {
                if (x < e.getX()) {
                    cam.setX(cam.getX() + 1 / scale);
                } else {
                    cam.setX(cam.getX() - 1 / scale);
                }
                if (y < e.getY()) {
                    cam.setY(cam.getY() + 1 / scale);
                } else {
                    cam.setY(cam.getY() - 1 / scale);
                }
            }else if(Math.abs(x2-e.getX())<10 && Math.abs(y2-e.getY())<10) {
                if (x2 < e.getX()) {
                    to.setX(to.getX() + 1 / scale);
                } else {
                    to.setX(to.getX() - 1 / scale);
                }
                if (y2 < e.getY()) {
                    to.setY(to.getY() + 1 / scale);
                } else {
                    to.setY(to.getY() - 1 / scale);
                }
            }else if(Math.abs(x3-e.getX())<10 && Math.abs(y3-e.getY())<10) {
                if (x3 < e.getX()) {
                    light.setX(light.getX() + 1 / scale);
                } else {
                    light.setX(light.getX() - 1 / scale);
                }
                if (y3 < e.getY()) {
                    light.setY(light.getY() + 1 / scale);
                } else {
                    light.setY(light.getY() - 1 / scale);
                }
            }
                tXY = new Matrix4f().initTranslation(bitmapXY.getWidth() / 2, bitmapXY.getHeight() / 2, 0);
                s = new Matrix4f().initScale(scale, scale, scale);
                transform0 = tXY.mul(s);
                x = (int) (new Vector3f(transform0.transform(new Vector4f(cam, 1))).getX());
                y = (int) (new Vector3f(transform0.transform(new Vector4f(cam, 1))).getY());
                x2 = (int)(new Vector3f(transform0.transform(new Vector4f(to, 1))).getX());
                y2 = (int)(new Vector3f(transform0.transform(new Vector4f(to, 1))).getY());
                x3 = (int)(new Vector3f(transform0.transform(new Vector4f(light, 1))).getX());
                y3 = (int)(new Vector3f(transform0.transform(new Vector4f(light, 1))).getY());
                updateTransformMatrix();
            xCameraTF.setText(Integer.toString((int)cam.getX()));
            yCameraTF.setText(Integer.toString((int)cam.getY()));
            zCameraTF.setText(Integer.toString((int)cam.getZ()));

            xCameraToTF.setText(Integer.toString((int)to.getX()));
            yCameraToTF.setText(Integer.toString((int)to.getY()));
            zCameraToTF.setText(Integer.toString((int)to.getZ()));

            xLightTF.setText(Integer.toString((int)light.getX()));
            yLightTF.setText(Integer.toString((int)light.getY()));
            zLightTF.setText(Integer.toString((int)light.getZ()));

        }

        public void mouseMoved(MouseEvent e)
        {
        }

        public void mouseClicked(MouseEvent e)
        {
        }

        public void mouseEntered(MouseEvent e)
        {
        }

        public void mouseExited(MouseEvent e)
        {
        }

        public void mousePressed(MouseEvent e)
        {
        }

        public void mouseReleased(MouseEvent e)
        {

        }

    }

    public class MouseListenerXZ implements MouseListener, MouseMotionListener
    {

        Matrix4f tXZ = new Matrix4f().initTranslation(bitmapXZ.getWidth()/2, 0, bitmapXZ.getHeight()/2);
        Matrix4f s = new Matrix4f().initScale(scale, scale, scale);
        Matrix4f transform1 = tXZ.mul(s);
        int x = (int)(new Vector3f(transform1.transform(new Vector4f(cam, 1))).getX());
        int z = (int)(new Vector3f(transform1.transform(new Vector4f(cam, 1))).getZ());
        int x2 = (int)(new Vector3f(transform1.transform(new Vector4f(to, 1))).getX());
        int z2 = (int)(new Vector3f(transform1.transform(new Vector4f(to, 1))).getZ());
        int x3 = (int)(new Vector3f(transform1.transform(new Vector4f(light, 1))).getX());
        int z3 = (int)(new Vector3f(transform1.transform(new Vector4f(light, 1))).getZ());

        public void mouseDragged(MouseEvent e)
        {
            if(Math.abs(x-e.getX())<10 && Math.abs(z-e.getY())<10) {
                if (x < e.getX()) {
                    cam.setX(cam.getX() + 1 / scale);
                } else {
                    cam.setX(cam.getX() - 1 / scale);
                }
                if (z < e.getY()) {
                    cam.setZ(cam.getZ() + 1 / scale);
                } else {
                    cam.setZ(cam.getZ() - 1 / scale);
                }
            }else if(Math.abs(x2-e.getX())<10 && Math.abs(z2-e.getY())<10) {
                if (x2 < e.getX()) {
                    to.setX(to.getX() + 1 / scale);
                } else {
                    to.setX(to.getX() - 1 / scale);
                }
                if (z2 < e.getY()) {
                    to.setZ(to.getZ() + 1 / scale);
                } else {
                    to.setZ(to.getZ() - 1 / scale);
                }
            }else if(Math.abs(x3-e.getX())<10 && Math.abs(z3-e.getY())<10) {
                if (x3 < e.getX()) {
                    light.setX(light.getX() + 1 / scale);
                } else {
                    light.setX(light.getX() - 1 / scale);
                }
                if (z3 < e.getY()) {
                    light.setZ(light.getZ() + 1 / scale);
                } else {
                    light.setZ(light.getZ() - 1 / scale);
                }
            }
            tXZ = new Matrix4f().initTranslation(bitmapXZ.getWidth()/2, 0, bitmapXZ.getHeight()/2);
            s = new Matrix4f().initScale(scale, scale, scale);
            transform1 = tXZ.mul(s);
            x = (int)(new Vector3f(transform1.transform(new Vector4f(cam, 1))).getX());
            z = (int)(new Vector3f(transform1.transform(new Vector4f(cam, 1))).getZ());
            x2 = (int)(new Vector3f(transform1.transform(new Vector4f(to, 1))).getX());
            z2 = (int)(new Vector3f(transform1.transform(new Vector4f(to, 1))).getZ());
            x3 = (int)(new Vector3f(transform1.transform(new Vector4f(light, 1))).getX());
            z3 = (int)(new Vector3f(transform1.transform(new Vector4f(light, 1))).getZ());
            updateTransformMatrix();
            xCameraTF.setText(Integer.toString((int)cam.getX()));
            yCameraTF.setText(Integer.toString((int)cam.getY()));
            zCameraTF.setText(Integer.toString((int)cam.getZ()));

            xCameraToTF.setText(Integer.toString((int)to.getX()));
            yCameraToTF.setText(Integer.toString((int)to.getY()));
            zCameraToTF.setText(Integer.toString((int)to.getZ()));

            xLightTF.setText(Integer.toString((int)light.getX()));
            yLightTF.setText(Integer.toString((int)light.getY()));
            zLightTF.setText(Integer.toString((int)light.getZ()));
        }


        public void mouseMoved(MouseEvent e)
        {

        }

        public void mouseClicked(MouseEvent e)
        {
        }

        public void mouseEntered(MouseEvent e)
        {
        }

        public void mouseExited(MouseEvent e)
        {
        }

        public void mousePressed(MouseEvent e)
        {

        }

        public void mouseReleased(MouseEvent e)
        {

        }

    }

    public class MouseListenerYZ implements MouseListener, MouseMotionListener
    {
        Matrix4f tYZ = new Matrix4f().initTranslation(0, bitmapYZ.getWidth()/2, bitmapYZ.getHeight()/2);
        Matrix4f s = new Matrix4f().initScale(scale, scale, scale);
        Matrix4f transform2 = tYZ.mul(s);
        int y = (int)(new Vector3f(transform2.transform(new Vector4f(cam, 1))).getY());
        int z = (int)(new Vector3f(transform2.transform(new Vector4f(cam, 1))).getZ());
        int y2 = (int)(new Vector3f(transform2.transform(new Vector4f(to, 1))).getY());
        int z2 = (int)(new Vector3f(transform2.transform(new Vector4f(to, 1))).getZ());
        int y3 = (int)(new Vector3f(transform2.transform(new Vector4f(light, 1))).getY());
        int z3 = (int)(new Vector3f(transform2.transform(new Vector4f(light, 1))).getZ());

        public void mouseDragged(MouseEvent e)
        {
            if(Math.abs(y-e.getX())<10 && Math.abs(z-e.getY())<10) {
                if (y < e.getX()) {
                    cam.setY(cam.getY() + 1 / scale);
                } else {
                    cam.setY(cam.getY() - 1 / scale);
                }
                if (z < e.getY()) {
                    cam.setZ(cam.getZ() + 1 / scale);
                } else {
                    cam.setZ(cam.getZ() - 1 / scale);
                }
            }else if(Math.abs(y2-e.getX())<10 && Math.abs(z2-e.getY())<10) {
                if (y2 < e.getX()) {
                    to.setY(to.getY() + 1 / scale);
                } else {
                    to.setY(to.getY() - 1 / scale);
                }
                if (z2 < e.getY()) {
                    to.setZ(to.getZ() + 1 / scale);
                } else {
                    to.setZ(to.getZ() - 1 / scale);
                }
            }else if(Math.abs(y3-e.getX())<10 && Math.abs(z3-e.getY())<10) {
                if (y3 < e.getX()) {
                    light.setY(light.getY() + 1 / scale);
                } else {
                    light.setY(light.getY() - 1 / scale);
                }
                if (z3 < e.getY()) {
                    light.setZ(light.getZ() + 1 / scale);
                } else {
                    light.setZ(light.getZ() - 1 / scale);
                }
            }
            tYZ = new Matrix4f().initTranslation(0, bitmapYZ.getWidth()/2, bitmapYZ.getHeight()/2);
            s = new Matrix4f().initScale(scale, scale, scale);
            transform2 = tYZ.mul(s);
            y = (int)(new Vector3f(transform2.transform(new Vector4f(cam, 1))).getY());
            z = (int)(new Vector3f(transform2.transform(new Vector4f(cam, 1))).getZ());
            y2 = (int)(new Vector3f(transform2.transform(new Vector4f(to, 1))).getY());
            z2 = (int)(new Vector3f(transform2.transform(new Vector4f(to, 1))).getZ());
            y3 = (int)(new Vector3f(transform2.transform(new Vector4f(light, 1))).getY());
            z3 = (int)(new Vector3f(transform2.transform(new Vector4f(light, 1))).getZ());
            updateTransformMatrix();
            xCameraTF.setText(Integer.toString((int)cam.getX()));
            yCameraTF.setText(Integer.toString((int)cam.getY()));
            zCameraTF.setText(Integer.toString((int)cam.getZ()));

            xCameraToTF.setText(Integer.toString((int)to.getX()));
            yCameraToTF.setText(Integer.toString((int)to.getY()));
            zCameraToTF.setText(Integer.toString((int)to.getZ()));

            xLightTF.setText(Integer.toString((int)light.getX()));
            yLightTF.setText(Integer.toString((int)light.getY()));
            zLightTF.setText(Integer.toString((int)light.getZ()));
        }

        public void mouseMoved(MouseEvent e)
        {

        }

        public void mouseClicked(MouseEvent e)
        {
        }

        public void mouseEntered(MouseEvent e)
        {
        }

        public void mouseExited(MouseEvent e)
        {
        }

        public void mousePressed(MouseEvent e)
        {

        }

        public void mouseReleased(MouseEvent e)
        {

        }

    }
}
