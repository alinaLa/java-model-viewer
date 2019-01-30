import org.lwjgl.openvr.HmdMatrix44;

public class Matrix4f
{

    private float[][] m;

    public Matrix4f()
    {
        m = new float[4][4];
    }

    public Matrix4f(float[][] m)
    {
        this.m = m.clone();
    }

    public Matrix4f mul(Matrix4f other)
    {
        float[][] result = new float[4][4];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                for (int k = 0; k < 4; k++)
                    result[i][j] += this.m[i][k] * other.m[k][j];
        return new Matrix4f(result);
    }

    public Vector4f transform(Vector4f v)
    {
        return new Vector4f(
                m[0][0] * v.getX() + m[0][1] * v.getY() + m[0][2] * v.getZ() + m[0][3] * v.getW(),
                m[1][0] * v.getX() + m[1][1] * v.getY() + m[1][2] * v.getZ() + m[1][3] * v.getW(),
                m[2][0] * v.getX() + m[2][1] * v.getY() + m[2][2] * v.getZ() + m[2][3] * v.getW(),
                m[3][0] * v.getX() + m[3][1] * v.getY() + m[3][2] * v.getZ() + m[3][3] * v.getW()
        );
    }

    public float get(int i)
    {
        return m[i / 4][i % 4];
    }

    public void set(int i, float f)
    {
        m[i / 4][i % 4] = f;
    }

    public float get(int x, int y)
    {
        return m[x][y];
    }

    public void set(int x, int y, float f)
    {
        m[x][y] = f;
    }

    public Matrix4f initIndentity()
    {
        m[0][0] = 1;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = 0;
        m[1][0] = 0;	m[1][1] = 1;	m[1][2] = 0;	m[1][3] = 0;
        m[2][0] = 0;	m[2][1] = 0;	m[2][2] = 1;	m[2][3] = 0;
        m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;
        return this;
    }

    public Matrix4f initTranslation(float x, float y, float z)
    {
        m[0][0] = 1;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = x;
        m[1][0] = 0;	m[1][1] = 1;	m[1][2] = 0;	m[1][3] = y;
        m[2][0] = 0;	m[2][1] = 0;	m[2][2] = 1;	m[2][3] = z;
        m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;
        return this;
    }

    public Matrix4f initRotation(float x, float y, float z)
    {
        double xx = Math.toRadians(x);
        double yy = Math.toRadians(y);
        double zz = Math.toRadians(z);
        Matrix4f rx = new Matrix4f();
        Matrix4f ry = new Matrix4f();
        Matrix4f rz = new Matrix4f();

        rz.m[0][0] = (float)Math.cos(zz);	rz.m[0][1] = -(float)Math.sin(zz);	rz.m[0][2] = 0;						rz.m[0][3] = 0;
        rz.m[1][0] = (float)Math.sin(zz);	rz.m[1][1] = (float)Math.cos(zz);	rz.m[1][2] = 0;						rz.m[1][3] = 0;
        rz.m[2][0] = 0;						rz.m[2][1] = 0;						rz.m[2][2] = 1;						rz.m[2][3] = 0;
        rz.m[3][0] = 0;						rz.m[3][1] = 0;						rz.m[3][2] = 0;						rz.m[3][3] = 1;

        rx.m[0][0] = 1;						rx.m[0][1] = 0;						rx.m[0][2] = 0;						rx.m[0][3] = 0;
        rx.m[1][0] = 0;						rx.m[1][1] = (float)Math.cos(xx);	rx.m[1][2] = -(float)Math.sin(xx);	rx.m[1][3] = 0;
        rx.m[2][0] = 0;						rx.m[2][1] = (float)Math.sin(xx);	rx.m[2][2] = (float)Math.cos(xx);	rx.m[2][3] = 0;
        rx.m[3][0] = 0;						rx.m[3][1] = 0;						rx.m[3][2] = 0;						rx.m[3][3] = 1;

        ry.m[0][0] = (float)Math.cos(yy);	ry.m[0][1] = 0;						ry.m[0][2] = -(float)Math.sin(yy);	ry.m[0][3] = 0;
        ry.m[1][0] = 0;						ry.m[1][1] = 1;						ry.m[1][2] = 0;						ry.m[1][3] = 0;
        ry.m[2][0] = (float)Math.sin(yy);	ry.m[2][1] = 0;						ry.m[2][2] = (float)Math.cos(yy);	ry.m[2][3] = 0;
        ry.m[3][0] = 0;						ry.m[3][1] = 0;						ry.m[3][2] = 0;						ry.m[3][3] = 1;

        m = rz.mul(ry.mul(rx)).m.clone();
        return this;
    }

    public Matrix4f initScale(float x, float y, float z)
    {
        m[0][0] = x;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = 0;
        m[1][0] = 0;	m[1][1] = y;	m[1][2] = 0;	m[1][3] = 0;
        m[2][0] = 0;	m[2][1] = 0;	m[2][2] = z;	m[2][3] = 0;
        m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;
        return this;
    }

    public Matrix4f wyznaczMacierzOdwrotna() {
            float[][] macierzOdwrotna;

                macierzOdwrotna = new float[4][4];
                float[][] macierzDolaczona = new float[4][4];
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        float[][] temp = new float[4 - 1][4 - 1];
                        int a = 0, b = 0;
                        for (int w = 0; w < 4; w++) {
                            for (int z = 0; z < 4; z++) {
                                if (w != i && z != j) {
                                    if (b >= temp.length) {
                                        b = 0;
                                        a++;
                                    }
                                    temp[a][b] = this.get(w, z);
                                    b++;
                                }
                            }
                        }

                        float wyznacznikTemp = this.wyznaczWyznacznikMacierzy(temp);

                        if ((i + j) % 2 != 0) {//Niparzyste czyli zmiana znaku wyznacznika
                            if (wyznacznikTemp > 0) {
                                wyznacznikTemp -= 2 * wyznacznikTemp;
                            } else {
                                wyznacznikTemp -= 2 * wyznacznikTemp;
                            }
                        } else {
                        }
                        macierzDolaczona[i][j] = wyznacznikTemp;
                    }
                }
                macierzDolaczona = this.transponujTablice(macierzDolaczona);
                macierzOdwrotna = this.pomnozPrzezSkalarTablice(1 / this.wyznaczWyznacznik(), macierzDolaczona);
                return new Matrix4f(macierzOdwrotna);


    }

    private float wyznaczWyznacznikMacierzy(float[][] tablica) {
        float wyznacznik = 0;

        if (tablica.length == 1 && tablica[0].length == 1) {
            wyznacznik = tablica[0][0];
        } else if (tablica.length != tablica[0].length) {
            throw new RuntimeException("Nie można wyznaczyć wyznacznika dla macierzy która nie jest kwadratowa");
        } else if (tablica.length == 2 && tablica[0].length == 2) {
            wyznacznik = (tablica[0][0] * tablica[1][1] - tablica[0][1] * tablica[1][0]);
        } else {
            double[][] nTab = new double[tablica.length + (tablica.length - 1)][tablica[0].length];
            for (int i = 0, _i = 0; i < nTab.length; i++, _i++) {
                for (int j = 0; j < tablica[0].length; j++) {
                    if (_i < tablica.length && j < tablica[0].length) {
                        nTab[i][j] = tablica[_i][j];
                    } else {
                        _i = 0;
                        nTab[i][j] = tablica[_i][j];
                    }
                }
            }

            double iloczyn = 1;
            int _i;

            for (int i = 0; i < tablica.length; i++) {
                _i = i;
                for (int j = 0; j < tablica[0].length; j++) {
                    iloczyn *= nTab[_i][j];
                    _i++;
                }
                wyznacznik += iloczyn;
                iloczyn = 1;
            }

            iloczyn = 1;
            for (int i = 0; i < tablica.length; i++) {
                _i = i;
                for (int j = tablica[0].length - 1; j >= 0; j--) {
                    iloczyn *= nTab[_i][j];
                    _i++;
                }
                wyznacznik -= iloczyn;
                iloczyn = 1;
            }
        }
        return wyznacznik;
    }

    private float[][] transponujTablice(float[][] tablica) {
        float[][] macierzTransponowana = new float[tablica[0].length][tablica.length];
        for (int i = 0; i < tablica.length; i++) {
            for (int j = 0; j < tablica[0].length; j++) {
                macierzTransponowana[j][i] = tablica[i][j];
            }
        }
        return macierzTransponowana;
    }

    private float[][] pomnozPrzezSkalarTablice(float skalar, float[][] tablica) {
        float[][] macierzPomnozona = new float[tablica.length][tablica[0].length];
        for (int i = 0; i < tablica.length; i++) {
            for (int j = 0; j < tablica[0].length; j++) {
                macierzPomnozona[i][j] = (tablica[i][j] * skalar);
            }
        }
        return macierzPomnozona;
    }

    public float wyznaczWyznacznik() {
        return this.wyznaczWyznacznikMacierzy(this.m);
    }




}
