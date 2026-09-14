
import java.io.DataInputStream;
import java.io.IOException;

public class ProblemaP1 {

    private static final long INF = Long.MAX_VALUE / 4;

    public static void main(String[] args) throws IOException {
        LectorRapido in = new LectorRapido();
        StringBuilder salida = new StringBuilder();

        int casos = in.nextInt();
        for (int c = 0; c < casos; c++) {
            salida.append(resolverCaso(in)).append('\n');
        }
        System.out.print(salida);
    }

    private static String resolverCaso(LectorRapido in) throws IOException {
        int n = in.nextInt();         
        int m = in.nextInt();         
        int p = in.nextInt();         

        int[] e = new int[n + 1];      
        for (int i = 1; i <= n; i++) {
            e[i] = in.nextInt();
        }


        int[] posSalida = new int[p];      
        int[] orbitaLlegada = new int[p];  
        int[] posLlegada = new int[p];     

        int[] cabezaSalen = new int[n + 2];   
        int[] sigSalen = new int[p];
        java.util.Arrays.fill(cabezaSalen, -1);

        for (int j = 0; j < p; j++) {
            int xs = in.nextInt();
            int ys = in.nextInt();
            int xe = in.nextInt();
            int ye = in.nextInt();
            posSalida[j] = ys;
            orbitaLlegada[j] = xe;
            posLlegada[j] = ye;
            sigSalen[j] = cabezaSalen[xs];
            cabezaSalen[xs] = j;
        }

        
        int[] cabezaLlegan = new int[n + 2];
        int[] sigLlegan = new int[p];
        long[] costoLlegada = new long[p];
        java.util.Arrays.fill(cabezaLlegan, -1);

        long[] costo = new long[m + 2];   
        long respuesta = INF;

        for (int i = 1; i <= n; i++) {
            
            for (int y = 1; y <= m; y++) {
                costo[y] = INF;
            }
            if (i == 1) {
                costo[1] = 0;                 
            }
            for (int j = cabezaLlegan[i]; j != -1; j = sigLlegan[j]) {
                if (costoLlegada[j] < costo[posLlegada[j]]) {
                    costo[posLlegada[j]] = costoLlegada[j];
                }
            }

            
            for (int y = 2; y <= m; y++) {
                long v = costo[y - 1] + e[i];
                if (v < costo[y]) costo[y] = v;
            }
            for (int y = m - 1; y >= 1; y--) {
                long v = costo[y + 1] + e[i];
                if (v < costo[y]) costo[y] = v;
            }

            
            if (i == n) {
                respuesta = costo[m];
                break;
            }

            
            for (int j = cabezaSalen[i]; j != -1; j = sigSalen[j]) {
                long v = costo[posSalida[j]];
                if (v < INF) {
                    int destino = orbitaLlegada[j];
                    costoLlegada[j] = v;
                    sigLlegan[j] = cabezaLlegan[destino];
                    cabezaLlegan[destino] = j;
                }
            }
        }

        return (respuesta >= INF) ? "NO EXISTE" : Long.toString(respuesta);
    }

    
    private static class LectorRapido {
        private static final int TAM = 1 << 16;
        private final DataInputStream din = new DataInputStream(System.in);
        private final byte[] buffer = new byte[TAM];
        private int punteroBuffer = 0;
        private int bytesLeidos = 0;

        int nextInt() throws IOException {
            int ret = 0;
            int b = leer();
            while (b <= ' ') {
                b = leer();
            }
            boolean negativo = (b == '-');
            if (negativo) b = leer();
            while (b >= '0' && b <= '9') {
                ret = ret * 10 + (b - '0');
                b = leer();
            }
            return negativo ? -ret : ret;
        }

        private int leer() throws IOException {
            if (punteroBuffer == bytesLeidos) {
                bytesLeidos = din.read(buffer, 0, TAM);
                punteroBuffer = 0;
                if (bytesLeidos == -1) return -1;
            }
            return buffer[punteroBuffer++];
        }
    }
}
