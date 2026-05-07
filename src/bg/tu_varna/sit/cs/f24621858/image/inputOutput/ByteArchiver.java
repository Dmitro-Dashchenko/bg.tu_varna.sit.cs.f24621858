package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

public class ByteArchiver {

    public static int[][] pack(int[][] bits, int bitsInRow) {

        int rows = bits.length;
        int columns = (bitsInRow + 7) / 8;

        int[][] packed = new int[rows][columns];

        for (int i = 0; i < rows; i++) {

            int byteIndex = 0;
            int currentByte = 0;
            int bitCount = 0;

            for (int j = 0; j < bitsInRow; j++) {

                int bit = bits[i][j] & 1;

                currentByte <<= 1;
                currentByte |= bit;

                bitCount++;

                if (bitCount == Byte.SIZE) {
                    packed[i][byteIndex++] = currentByte;
                    currentByte = 0;
                    bitCount = 0;
                }
            }

            if (bitCount != 0) {
                currentByte <<= (Byte.SIZE - bitCount);
                packed[i][byteIndex] = currentByte;
            }
        }

        return packed;
    }

    public static int[][] unpack(int[][] packedBytes, int bitsInRow){

        int rows = packedBytes.length;
        int columns = packedBytes[0].length;

        int[][] bits = new int[rows][bitsInRow];

        for (int i = 0; i < rows; i++) {

            int rowsBit = 0;

            for (int j = 0; j < columns; j++) {
                int currentByte = packedBytes[i][j];

                for(int k = 0; k < Byte.SIZE && rowsBit < bitsInRow;k++) {
                    byte bit = (byte)((currentByte >> (7 - k)) & 1);
                    bits[i][rowsBit++] = bit;
                }
            }
        }

        return bits;
    }
}

