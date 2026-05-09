package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

/**
 * Utility class for packing and unpacking binary pixel data
 * into compact byte-aligned representations.
 *
 * <p>This class is primarily used for handling binary image formats.
 *
 * <p>The packing process converts arrays of bits ({@code 0} or {@code 1})
 * into arrays of bytes represented as integers.</p>
 *
 * <p>The unpacking process restores the original bit representation
 * from packed byte data.</p>
 *
 * <p>Each row is packed independently. If the number of bits in a row
 * is not divisible by 8, the remaining bits are padded with zeros
 * in the least significant positions of the final byte.</p>
 *
 * <p>This class cannot be instantiated.</p>
 *
 * @author Dmitro Dashchenko
 */
public class ByteArchiver {

    /**
     * Packs binary bit data into byte-aligned integer arrays.
     *
     * <p>Each group of 8 bits is combined into a single byte.
     * Bits are packed from left to right using big-endian bit order.</p>
     *
     * <p>If the row length is not divisible by 8,
     * the last byte is padded with zeros.</p>
     *
     * @param bits the source bit matrix containing only {@code 0} and {@code 1}
     * @param bitsInRow the number of valid bits in each row
     *
     * @return a packed byte matrix where each integer represents one byte
     */
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

    /**
     * Unpacks byte-aligned binary data back into individual bits.
     *
     * <p>Each byte is expanded into 8 separate bits using
     * big-endian bit order.</p>
     *
     * <p>Only {@code bitsInRow} bits are restored for each row,
     * ignoring any padding bits that may exist in the final byte.</p>
     *
     * @param packedBytes the packed byte matrix
     * @param bitsInRow the number of valid bits expected in each row
     *
     * @return a matrix containing unpacked binary bits ({@code 0} or {@code 1})
     */
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

