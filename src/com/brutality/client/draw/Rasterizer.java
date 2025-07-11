package com.brutality.client.draw;

import com.brutality.client.Client;
import com.brutality.client.Configuration;
import com.brutality.client.cache.StreamLoader;
import com.brutality.client.cache.graphics.Background;
import com.brutality.client.map.WorldController;

public final class Rasterizer extends DrawingArea {
	
	   public static void nullLoader()
	{
		anIntArray1468 = null;
		anIntArray1468 = null;
		anIntArray1470 = null;
		anIntArray1471 = null;
		lineOffsets = null;
		aBackgroundArray1474s = null;
		aBooleanArray1475 = null;
		anIntArray1476 = null;
		anIntArrayArray1478 = null;
		anIntArrayArray1479 = null;
		anIntArray1480 = null;
		anIntArray1482 = null;
		anIntArrayArray1483 = null;
		RendererLock.kill();
	}
	

	private static final int FOG_BEGIN = 2000;
	private static final int FOG_END = 3000;

	
	public static void drawFogTriangle(int y1, int y2, int y3, int x1, int x2,
			int x3, int z1, int z2, int z3) {
		if (z1 <= FOG_BEGIN && z2 <= FOG_BEGIN && z3 <= FOG_BEGIN) {
			return;
		}
		int j2 = 0;
		int k2 = 0;

		if (y2 != y1) {
			j2 = (x2 - x1 << 16) / (y2 - y1);
			k2 = (z2 - z1 << 16) / (y2 - y1);
		}

		int l2 = 0;
		int i3 = 0;

		if (y3 != y2) {
			l2 = (x3 - x2 << 16) / (y3 - y2);
			i3 = (z3 - z2 << 16) / (y3 - y2);
		}

		int j3 = 0;
		int k3 = 0;

		if (y3 != y1) {
			j3 = (x1 - x3 << 16) / (y1 - y3);
			k3 = (z1 - z3 << 16) / (y1 - y3);
		}

		if (y1 <= y2 && y1 <= y3) {
			if (y1 >= DrawingArea.bottomY) {
				return;
			}

			if (y2 > DrawingArea.bottomY) {
				y2 = DrawingArea.bottomY;
			}

			if (y3 > DrawingArea.bottomY) {
				y3 = DrawingArea.bottomY;
			}

			if (y2 < y3) {
				x3 = x1 <<= 16;
				z3 = z1 <<= 16;

				if (y1 < 0) {
					x3 -= j3 * y1;
					x1 -= j2 * y1;
					z3 -= k3 * y1;
					z1 -= k2 * y1;
					y1 = 0;
				}

				x2 <<= 16;
				z2 <<= 16;

				if (y2 < 0) {
					x2 -= l2 * y2;
					z2 -= i3 * y2;
					y2 = 0;
				}

				if (y1 != y2 && j3 < j2 || y1 == y2 && j3 > l2) {
					y3 -= y2;
					y2 -= y1;

					for (y1 = lineOffsets[y1]; --y2 >= 0; y1 += DrawingArea.width) {
						drawFogScanline(DrawingArea.pixels, y1, x3 >> 16,
								x1 >> 16, z3, z1);
						x3 += j3;
						x1 += j2;
						z3 += k3;
						z1 += k2;
					}

					while (--y3 >= 0) {
						drawFogScanline(DrawingArea.pixels, y1, x3 >> 16,
								x2 >> 16, z3, z2);
						x3 += j3;
						x2 += l2;
						z3 += k3;
						z2 += i3;
						y1 += DrawingArea.width;
					}

					return;
				}

				y3 -= y2;
				y2 -= y1;

				for (y1 = lineOffsets[y1]; --y2 >= 0; y1 += DrawingArea.width) {
					drawFogScanline(DrawingArea.pixels, y1, x1 >> 16, x3 >> 16,
							z1, z3);
					x3 += j3;
					x1 += j2;
					z3 += k3;
					z1 += k2;
				}

				while (--y3 >= 0) {
					drawFogScanline(DrawingArea.pixels, y1, x2 >> 16, x3 >> 16,
							z2, z3);
					x3 += j3;
					x2 += l2;
					z3 += k3;
					z2 += i3;
					y1 += DrawingArea.width;
				}

				return;
			}

			x2 = x1 <<= 16;
			z2 = z1 <<= 16;

			if (y1 < 0) {
				x2 -= j3 * y1;
				x1 -= j2 * y1;
				z2 -= k3 * y1;
				z1 -= k2 * y1;
				y1 = 0;
			}

			x3 <<= 16;
			z3 <<= 16;

			if (y3 < 0) {
				x3 -= l2 * y3;
				z3 -= i3 * y3;
				y3 = 0;
			}

			if (y1 != y3 && j3 < j2 || y1 == y3 && l2 > j2) {
				y2 -= y3;
				y3 -= y1;

				for (y1 = lineOffsets[y1]; --y3 >= 0; y1 += DrawingArea.width) {
					drawFogScanline(DrawingArea.pixels, y1, x2 >> 16, x1 >> 16,
							z2, z1);
					x2 += j3;
					x1 += j2;
					z2 += k3;
					z1 += k2;
				}

				while (--y2 >= 0) {
					drawFogScanline(DrawingArea.pixels, y1, x3 >> 16, x1 >> 16,
							z3, z1);
					x3 += l2;
					x1 += j2;
					z3 += i3;
					z1 += k2;
					y1 += DrawingArea.width;
				}

				return;
			}

			y2 -= y3;
			y3 -= y1;

			for (y1 = lineOffsets[y1]; --y3 >= 0; y1 += DrawingArea.width) {
				drawFogScanline(DrawingArea.pixels, y1, x1 >> 16, x2 >> 16, z1,
						z2);
				x2 += j3;
				x1 += j2;
				z2 += k3;
				z1 += k2;
			}

			while (--y2 >= 0) {
				drawFogScanline(DrawingArea.pixels, y1, x1 >> 16, x3 >> 16, z1,
						z3);
				x3 += l2;
				x1 += j2;
				z3 += i3;
				z1 += k2;
				y1 += DrawingArea.width;
			}

			return;
		}

		if (y2 <= y3) {
			if (y2 >= DrawingArea.bottomY) {
				return;
			}

			if (y3 > DrawingArea.bottomY) {
				y3 = DrawingArea.bottomY;
			}

			if (y1 > DrawingArea.bottomY) {
				y1 = DrawingArea.bottomY;
			}

			if (y3 < y1) {
				x1 = x2 <<= 16;
				z1 = z2 <<= 16;

				if (y2 < 0) {
					x1 -= j2 * y2;
					x2 -= l2 * y2;
					z1 -= k2 * y2;
					z2 -= i3 * y2;
					y2 = 0;
				}

				x3 <<= 16;
				z3 <<= 16;

				if (y3 < 0) {
					x3 -= j3 * y3;
					z3 -= k3 * y3;
					y3 = 0;
				}

				if (y2 != y3 && j2 < l2 || y2 == y3 && j2 > j3) {
					y1 -= y3;
					y3 -= y2;

					for (y2 = lineOffsets[y2]; --y3 >= 0; y2 += DrawingArea.width) {
						drawFogScanline(DrawingArea.pixels, y2, x1 >> 16,
								x2 >> 16, z1, z2);
						x1 += j2;
						x2 += l2;
						z1 += k2;
						z2 += i3;
					}

					while (--y1 >= 0) {
						drawFogScanline(DrawingArea.pixels, y2, x1 >> 16,
								x3 >> 16, z1, z3);
						x1 += j2;
						x3 += j3;
						z1 += k2;
						z3 += k3;
						y2 += DrawingArea.width;
					}

					return;
				}

				y1 -= y3;
				y3 -= y2;

				for (y2 = lineOffsets[y2]; --y3 >= 0; y2 += DrawingArea.width) {
					drawFogScanline(DrawingArea.pixels, y2, x2 >> 16, x1 >> 16,
							z2, z1);
					x1 += j2;
					x2 += l2;
					z1 += k2;
					z2 += i3;
				}

				while (--y1 >= 0) {
					drawFogScanline(DrawingArea.pixels, y2, x3 >> 16, x1 >> 16,
							z3, z1);
					x1 += j2;
					x3 += j3;
					z1 += k2;
					z3 += k3;
					y2 += DrawingArea.width;
				}

				return;
			}

			x3 = x2 <<= 16;
			z3 = z2 <<= 16;

			if (y2 < 0) {
				x3 -= j2 * y2;
				x2 -= l2 * y2;
				z3 -= k2 * y2;
				z2 -= i3 * y2;
				y2 = 0;
			}

			x1 <<= 16;
			z1 <<= 16;

			if (y1 < 0) {
				x1 -= j3 * y1;
				z1 -= k3 * y1;
				y1 = 0;
			}

			if (j2 < l2) {
				y3 -= y1;
				y1 -= y2;

				for (y2 = lineOffsets[y2]; --y1 >= 0; y2 += DrawingArea.width) {
					drawFogScanline(DrawingArea.pixels, y2, x3 >> 16, x2 >> 16,
							z3, z2);
					x3 += j2;
					x2 += l2;
					z3 += k2;
					z2 += i3;
				}

				while (--y3 >= 0) {
					drawFogScanline(DrawingArea.pixels, y2, x1 >> 16, x2 >> 16,
							z1, z2);
					x1 += j3;
					x2 += l2;
					z1 += k3;
					z2 += i3;
					y2 += DrawingArea.width;
				}

				return;
			}

			y3 -= y1;
			y1 -= y2;

			for (y2 = lineOffsets[y2]; --y1 >= 0; y2 += DrawingArea.width) {
				drawFogScanline(DrawingArea.pixels, y2, x2 >> 16, x3 >> 16, z2,
						z3);
				x3 += j2;
				x2 += l2;
				z3 += k2;
				z2 += i3;
			}

			while (--y3 >= 0) {
				drawFogScanline(DrawingArea.pixels, y2, x2 >> 16, x1 >> 16, z2,
						z1);
				x1 += j3;
				x2 += l2;
				z1 += k3;
				z2 += i3;
				y2 += DrawingArea.width;
			}
			return;
		}
		if (y3 >= DrawingArea.bottomY) {
			return;
		}
		if (y1 > DrawingArea.bottomY) {
			y1 = DrawingArea.bottomY;
		}
		if (y2 > DrawingArea.bottomY) {
			y2 = DrawingArea.bottomY;
		}
		if (y1 < y2) {
			x2 = x3 <<= 16;
			z2 = z3 <<= 16;
			if (y3 < 0) {
				x2 -= l2 * y3;
				x3 -= j3 * y3;
				z2 -= i3 * y3;
				z3 -= k3 * y3;
				y3 = 0;
			}
			x1 <<= 16;
			z1 <<= 16;
			if (y1 < 0) {
				x1 -= j2 * y1;
				z1 -= k2 * y1;
				y1 = 0;
			}
			if (l2 < j3) {
				y2 -= y1;
				y1 -= y3;
				for (y3 = lineOffsets[y3]; --y1 >= 0; y3 += DrawingArea.width) {
					drawFogScanline(DrawingArea.pixels, y3, x2 >> 16, x3 >> 16,
							z2, z3);
					x2 += l2;
					x3 += j3;
					z2 += i3;
					z3 += k3;
				}

				while (--y2 >= 0) {
					drawFogScanline(DrawingArea.pixels, y3, x2 >> 16, x1 >> 16,
							z2, z1);
					x2 += l2;
					x1 += j2;
					z2 += i3;
					z1 += k2;
					y3 += DrawingArea.width;
				}
				return;
			}
			y2 -= y1;
			y1 -= y3;
			for (y3 = lineOffsets[y3]; --y1 >= 0; y3 += DrawingArea.width) {
				drawFogScanline(DrawingArea.pixels, y3, x3 >> 16, x2 >> 16, z3,
						z2);
				x2 += l2;
				x3 += j3;
				z2 += i3;
				z3 += k3;
			}

			while (--y2 >= 0) {
				drawFogScanline(DrawingArea.pixels, y3, x1 >> 16, x2 >> 16, z1,
						z2);
				x2 += l2;
				x1 += j2;
				z2 += i3;
				z1 += k2;
				y3 += DrawingArea.width;
			}
			return;
		}
		x1 = x3 <<= 16;
		z1 = z3 <<= 16;
		if (y3 < 0) {
			x1 -= l2 * y3;
			x3 -= j3 * y3;
			z1 -= i3 * y3;
			z3 -= k3 * y3;
			y3 = 0;
		}
		x2 <<= 16;
		z2 <<= 16;
		if (y2 < 0) {
			x2 -= j2 * y2;
			z2 -= k2 * y2;
			y2 = 0;
		}
		if (l2 < j3) {
			y1 -= y2;
			y2 -= y3;
			for (y3 = lineOffsets[y3]; --y2 >= 0; y3 += DrawingArea.width) {
				drawFogScanline(DrawingArea.pixels, y3, x1 >> 16, x3 >> 16, z1,
						z3);
				x1 += l2;
				x3 += j3;
				z1 += i3;
				z3 += k3;
			}

			while (--y1 >= 0) {
				drawFogScanline(DrawingArea.pixels, y3, x2 >> 16, x3 >> 16, z2,
						z3);
				x2 += j2;
				x3 += j3;
				z2 += k2;
				z3 += k3;
				y3 += DrawingArea.width;
			}
			return;
		}
		y1 -= y2;
		y2 -= y3;
		for (y3 = lineOffsets[y3]; --y2 >= 0; y3 += DrawingArea.width) {
			drawFogScanline(DrawingArea.pixels, y3, x3 >> 16, x1 >> 16, z3, z1);
			x1 += l2;
			x3 += j3;
			z1 += i3;
			z3 += k3;
		}

		while (--y1 >= 0) {
			drawFogScanline(DrawingArea.pixels, y3, x3 >> 16, x2 >> 16, z3, z2);
			x2 += j2;
			x3 += j3;
			z2 += k2;
			z3 += k3;
			y3 += DrawingArea.width;
		}
	}

	private static void drawFogScanline(int[] dst, int off, int x1, int x2,
			int z1, int z2) {
		if (x1 >= x2) {
			return;
		}
		z2 = (z2 - z1) / (x2 - x1);
		if (aBoolean1462) {
			if (x2 > DrawingArea.centerX) {
				x2 = DrawingArea.centerX;
			}
			if (x1 < 0) {
				z1 -= x1 * z2;
				x1 = 0;
			}
			if (x1 >= x2) {
				return;
			}
		}
		int n = x2 - x1;
		off += x1;
		if (anInt1465 == 0) {
			while (--n >= 0) {
				int z = z1 >> 16;
				if (z > FOG_BEGIN) {
					if (z >= FOG_END) {
						dst[off] = Client.currentFogColor;
					} else {
						int rgb = dst[off];
						int a1 = (z - FOG_BEGIN << 8) / (FOG_END - FOG_BEGIN);
						int a2 = 256 - a1;

						int src = ((Client.currentFogColor & 0xff00ff) * a1 >> 8 & 0xff00ff)
								+ ((Client.currentFogColor & 0xff00) * a1 >> 8 & 0xff00);
						rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff)
								+ ((rgb & 0xff00) * a2 >> 8 & 0xff00);
						dst[off] = src + rgb;
					}
				}
				off++;
				z1 += z2;
			}
		} else {
			while (--n >= 0) {
				int z = z1 >> 16;
				if (z >= FOG_END) {
					dst[off] = Client.currentFogColor;
				} else {
					int rgb = dst[off];
					int a1 = (z - FOG_BEGIN) * (256 - anInt1465)
							/ (FOG_END - FOG_BEGIN);
					int a2 = 256 - a1;

					int src = ((Client.currentFogColor & 0xff00ff) * a1 >> 8 & 0xff00ff)
							+ ((Client.currentFogColor & 0xff00) * a1 >> 8 & 0xff00);
					rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff)
							+ ((rgb & 0xff00) * a2 >> 8 & 0xff00);
					dst[off] = src + rgb;
				}
				off++;
				z1 += z2;
			}
		}
	}

	static int[] animated = { 17, 24, 34, 40 };

	public static void drawTexturedFogTriangle(int y1, int y2, int y3, int x1,
			int x2, int x3, int z1, int z2, int z3, int j2, int k2, int l2,
			int i3, int j3, int k3, int l3, int i4, int j4, int tex) {
		if (!aBooleanArray1475[tex]) {
			drawFogTriangle(y1, y2, y3, x1, x2, x3, z1, z2, z3);
			return;
		}
		int[]ai = method371(tex);
		
		k2 = j2 - k2;
		j3 = i3 - j3;
		i4 = l3 - i4;
		l2 -= j2;
		k3 -= i3;
		j4 -= l3;
		int l4 = l2 * i3 - k3 * j2 << (WorldController.viewDistance == 9 ? 14 : 15);
		int i5 = k3 * l3 - j4 * i3 << 8;
		int j5 = j4 * j2 - l2 * l3 << 5;
		int k5 = k2 * i3 - j3 * j2 << (WorldController.viewDistance == 9 ? 14 : 15);
		int l5 = j3 * l3 - i4 * i3 << 8;
		int i6 = i4 * j2 - k2 * l3 << 5;
		int j6 = j3 * l2 - k2 * k3 << (WorldController.viewDistance == 9 ? 14 : 15);
		int k6 = i4 * k3 - j3 * j4 << 8;
		int l6 = k2 * j4 - i4 * l2 << 5;
		int i7 = 0;
		int j7 = 0;
		if (y2 != y1) {
			i7 = (x2 - x1 << 16) / (y2 - y1);
			j7 = (z2 - z1 << 16) / (y2 - y1);
		}
		int k7 = 0;
		int l7 = 0;
		if (y3 != y2) {
			k7 = (x3 - x2 << 16) / (y3 - y2);
			l7 = (z3 - z2 << 16) / (y3 - y2);
		}
		int i8 = 0;
		int j8 = 0;
		if (y3 != y1) {
			i8 = (x1 - x3 << 16) / (y1 - y3);
			j8 = (z1 - z3 << 16) / (y1 - y3);
		}
		if (y1 <= y2 && y1 <= y3) {
			if (y1 >= DrawingArea.bottomY) {
				return;
			}
			if (y2 > DrawingArea.bottomY) {
				y2 = DrawingArea.bottomY;
			}
			if (y3 > DrawingArea.bottomY) {
				y3 = DrawingArea.bottomY;
			}
			if (y2 < y3) {
				x3 = x1 <<= 16;
				z3 = z1 <<= 16;
				if (y1 < 0) {
					x3 -= i8 * y1;
					x1 -= i7 * y1;
					z3 -= j8 * y1;
					z1 -= j7 * y1;
					y1 = 0;
				}
				x2 <<= 16;
				z2 <<= 16;
				if (y2 < 0) {
					x2 -= k7 * y2;
					z2 -= l7 * y2;
					y2 = 0;
				}
				int k8 = y1 - centerY;
				l4 += j5 * k8;
				k5 += i6 * k8;
				j6 += l6 * k8;
				if (y1 != y2 && i8 < i7 || y1 == y2 && i8 > k7) {
					y3 -= y2;
					y2 -= y1;
					y1 = lineOffsets[y1];
					while (--y2 >= 0) {
						drawTexturedFogScanline( DrawingArea.pixels, ai,
								y1, x3 >> 16, x1 >> 16, z3, z1, l4, k5, j6, i5,
								l5, k6);
						x3 += i8;
						x1 += i7;
						z3 += j8;
						z1 += j7;
						y1 += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while (--y3 >= 0) {
						drawTexturedFogScanline( DrawingArea.pixels, ai,
								y1, x3 >> 16, x2 >> 16, z3, z2, l4, k5, j6, i5,
								l5, k6);
						x3 += i8;
						x2 += k7;
						z3 += j8;
						z2 += l7;
						y1 += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				y3 -= y2;
				y2 -= y1;
				y1 = lineOffsets[y1];
				while (--y2 >= 0) {
					drawTexturedFogScanline( DrawingArea.pixels, ai, y1,
							x1 >> 16, x3 >> 16, z1, z3, l4, k5, j6, i5, l5, k6);
					x3 += i8;
					x1 += i7;
					z3 += j8;
					z1 += j7;
					y1 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y3 >= 0) {
					drawTexturedFogScanline( DrawingArea.pixels, ai, y1,
							x2 >> 16, x3 >> 16, z2, z3, l4, k5, j6, i5, l5, k6);
					x3 += i8;
					x2 += k7;
					z3 += j8;
					z2 += l7;
					y1 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			x2 = x1 <<= 16;
			z2 = z1 <<= 16;
			if (y1 < 0) {
				x2 -= i8 * y1;
				x1 -= i7 * y1;
				z2 -= j8 * y1;
				z1 -= j7 * y1;
				y1 = 0;
			}
			x3 <<= 16;
			z3 <<= 16;
			if (y3 < 0) {
				x3 -= k7 * y3;
				z3 -= l7 * y3;
				y3 = 0;
			}
			int l8 = y1 - centerY;
			l4 += j5 * l8;
			k5 += i6 * l8;
			j6 += l6 * l8;
			if (y1 != y3 && i8 < i7 || y1 == y3 && k7 > i7) {
				y2 -= y3;
				y3 -= y1;
				y1 = lineOffsets[y1];
				while (--y3 >= 0) {
					drawTexturedFogScanline( DrawingArea.pixels, ai, y1,
							x2 >> 16, x1 >> 16, z2, z1, l4, k5, j6, i5, l5, k6);
					x2 += i8;
					x1 += i7;
					z2 += j8;
					z1 += j7;
					y1 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y2 >= 0) {
					drawTexturedFogScanline( DrawingArea.pixels, ai, y1,
							x3 >> 16, x1 >> 16, z3, z1, l4, k5, j6, i5, l5, k6);
					x3 += k7;
					x1 += i7;
					z3 += l7;
					z1 += j7;
					y1 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y2 -= y3;
			y3 -= y1;
			y1 = lineOffsets[y1];
			while (--y3 >= 0) {
				drawTexturedFogScanline( DrawingArea.pixels, ai, y1,
						x1 >> 16, x2 >> 16, z1, z2, l4, k5, j6, i5, l5, k6);
				x2 += i8;
				x1 += i7;
				z2 += j8;
				z1 += j7;
				y1 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y2 >= 0) {
				drawTexturedFogScanline( DrawingArea.pixels, ai, y1,
						x1 >> 16, x3 >> 16, z1, z3, l4, k5, j6, i5, l5, k6);
				x3 += k7;
				x1 += i7;
				z3 += l7;
				z1 += j7;
				y1 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if (y2 <= y3) {
			if (y2 >= DrawingArea.bottomY) {
				return;
			}
			if (y3 > DrawingArea.bottomY) {
				y3 = DrawingArea.bottomY;
			}
			if (y1 > DrawingArea.bottomY) {
				y1 = DrawingArea.bottomY;
			}
			if (y3 < y1) {
				x1 = x2 <<= 16;
				z1 = z2 <<= 16;
				if (y2 < 0) {
					x1 -= i7 * y2;
					x2 -= k7 * y2;
					z1 -= j7 * y2;
					z2 -= l7 * y2;
					y2 = 0;
				}
				x3 <<= 16;
				z3 <<= 16;
				if (y3 < 0) {
					x3 -= i8 * y3;
					z3 -= j8 * y3;
					y3 = 0;
				}
				int i9 = y2 - centerY;
				l4 += j5 * i9;
				k5 += i6 * i9;
				j6 += l6 * i9;
				if (y2 != y3 && i7 < k7 || y2 == y3 && i7 > i8) {
					y1 -= y3;
					y3 -= y2;
					y2 = lineOffsets[y2];
					while (--y3 >= 0) {
						drawTexturedFogScanline( DrawingArea.pixels, ai,
								y2, x1 >> 16, x2 >> 16, z1, z2, l4, k5, j6, i5,
								l5, k6);
						x1 += i7;
						x2 += k7;
						z1 += j7;
						z2 += l7;
						y2 += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while (--y1 >= 0) {
						drawTexturedFogScanline( DrawingArea.pixels, ai,
								y2, x1 >> 16, x3 >> 16, z1, z3, l4, k5, j6, i5,
								l5, k6);
						x1 += i7;
						x3 += i8;
						z1 += j7;
						z3 += j8;
						y2 += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				y1 -= y3;
				y3 -= y2;
				y2 = lineOffsets[y2];
				while (--y3 >= 0) {
					drawTexturedFogScanline( DrawingArea.pixels, ai, y2,
							x2 >> 16, x1 >> 16, z2, z1, l4, k5, j6, i5, l5, k6);
					x1 += i7;
					x2 += k7;
					z1 += j7;
					z2 += l7;
					y2 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y1 >= 0) {
					drawTexturedFogScanline( DrawingArea.pixels, ai, y2,
							x3 >> 16, x1 >> 16, z3, z1, l4, k5, j6, i5, l5, k6);
					x1 += i7;
					x3 += i8;
					z1 += j7;
					z3 += j8;
					y2 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			x3 = x2 <<= 16;
			z3 = z2 <<= 16;
			if (y2 < 0) {
				x3 -= i7 * y2;
				x2 -= k7 * y2;
				z3 -= j7 * y2;
				z2 -= l7 * y2;
				y2 = 0;
			}
			x1 <<= 16;
			z1 <<= 16;
			if (y1 < 0) {
				x1 -= i8 * y1;
				z1 -= j8 * y1;
				y1 = 0;
			}
			int j9 = y2 - centerY;
			l4 += j5 * j9;
			k5 += i6 * j9;
			j6 += l6 * j9;
			if (i7 < k7) {
				y3 -= y1;
				y1 -= y2;
				y2 = lineOffsets[y2];
				while (--y1 >= 0) {
					drawTexturedFogScanline( DrawingArea.pixels, ai, y2,
							x3 >> 16, x2 >> 16, z3, z2, l4, k5, j6, i5, l5, k6);
					x3 += i7;
					x2 += k7;
					z3 += j7;
					z2 += l7;
					y2 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y3 >= 0) {
					drawTexturedFogScanline( DrawingArea.pixels, ai, y2,
							x1 >> 16, x2 >> 16, z1, z2, l4, k5, j6, i5, l5, k6);
					x1 += i8;
					x2 += k7;
					z1 += j8;
					z2 += l7;
					y2 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y3 -= y1;
			y1 -= y2;
			y2 = lineOffsets[y2];
			while (--y1 >= 0) {
				drawTexturedFogScanline( DrawingArea.pixels, ai, y2,
						x2 >> 16, x3 >> 16, z2, z3, l4, k5, j6, i5, l5, k6);
				x3 += i7;
				x2 += k7;
				z3 += j7;
				z2 += l7;
				y2 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y3 >= 0) {
				drawTexturedFogScanline( DrawingArea.pixels, ai, y2,
						x2 >> 16, x1 >> 16, z2, z1, l4, k5, j6, i5, l5, k6);
				x1 += i8;
				x2 += k7;
				z1 += j8;
				z2 += l7;
				y2 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if (y3 >= DrawingArea.bottomY) {
			return;
		}
		if (y1 > DrawingArea.bottomY) {
			y1 = DrawingArea.bottomY;
		}
		if (y2 > DrawingArea.bottomY) {
			y2 = DrawingArea.bottomY;
		}
		if (y1 < y2) {
			x2 = x3 <<= 16;
			z2 = z3 <<= 16;
			if (y3 < 0) {
				x2 -= k7 * y3;
				x3 -= i8 * y3;
				z2 -= l7 * y3;
				z3 -= j8 * y3;
				y3 = 0;
			}
			x1 <<= 16;
			z1 <<= 16;
			if (y1 < 0) {
				x1 -= i7 * y1;
				z1 -= j7 * y1;
				y1 = 0;
			}
			int k9 = y3 - centerY;
			l4 += j5 * k9;
			k5 += i6 * k9;
			j6 += l6 * k9;
			if (k7 < i8) {
				y2 -= y1;
				y1 -= y3;
				y3 = lineOffsets[y3];
				while (--y1 >= 0) {
					drawTexturedFogScanline( DrawingArea.pixels, ai, y3,
							x2 >> 16, x3 >> 16, z2, z3, l4, k5, j6, i5, l5, k6);
					x2 += k7;
					x3 += i8;
					z2 += l7;
					z3 += j8;
					y3 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y2 >= 0) {
					drawTexturedFogScanline( DrawingArea.pixels, ai, y3,
							x2 >> 16, x1 >> 16, z2, z1, l4, k5, j6, i5, l5, k6);
					x2 += k7;
					x1 += i7;
					z2 += l7;
					z1 += j7;
					y3 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y2 -= y1;
			y1 -= y3;
			y3 = lineOffsets[y3];
			while (--y1 >= 0) {
				drawTexturedFogScanline( DrawingArea.pixels, ai, y3,
						x3 >> 16, x2 >> 16, z3, z2, l4, k5, j6, i5, l5, k6);
				x2 += k7;
				x3 += i8;
				z2 += l7;
				z3 += j8;
				y3 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y2 >= 0) {
				drawTexturedFogScanline( DrawingArea.pixels, ai, y3,
						x1 >> 16, x2 >> 16, z1, z2, l4, k5, j6, i5, l5, k6);
				x2 += k7;
				x1 += i7;
				z2 += l7;
				z1 += j7;
				y3 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		x1 = x3 <<= 16;
		z1 = z3 <<= 16;
		if (y3 < 0) {
			x1 -= k7 * y3;
			x3 -= i8 * y3;
			z1 -= l7 * y3;
			z3 -= j8 * y3;
			y3 = 0;
		}
		x2 <<= 16;
		z2 <<= 16;
		if (y2 < 0) {
			x2 -= i7 * y2;
			z2 -= j7 * y2;
			y2 = 0;
		}
		int l9 = y3 - centerY;
		l4 += j5 * l9;
		k5 += i6 * l9;
		j6 += l6 * l9;
		if (k7 < i8) {
			y1 -= y2;
			y2 -= y3;
			y3 = lineOffsets[y3];
			while (--y2 >= 0) {
				drawTexturedFogScanline( DrawingArea.pixels, ai, y3,
						x1 >> 16, x3 >> 16, z1, z3, l4, k5, j6, i5, l5, k6);
				x1 += k7;
				x3 += i8;
				z1 += l7;
				z3 += j8;
				y3 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y1 >= 0) {
				drawTexturedFogScanline( DrawingArea.pixels, ai, y3,
						x2 >> 16, x3 >> 16, z2, z3, l4, k5, j6, i5, l5, k6);
				x2 += i7;
				x3 += i8;
				z2 += j7;
				z3 += j8;
				y3 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		y1 -= y2;
		y2 -= y3;
		y3 = lineOffsets[y3];
		while (--y2 >= 0) {
			drawTexturedFogScanline( DrawingArea.pixels, ai, y3,
					x3 >> 16, x1 >> 16, z3, z1, l4, k5, j6, i5, l5, k6);
			x1 += k7;
			x3 += i8;
			z1 += l7;
			z3 += j8;
			y3 += DrawingArea.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
		while (--y1 >= 0) {
			drawTexturedFogScanline( DrawingArea.pixels, ai, y3,
					x3 >> 16, x2 >> 16, z3, z2, l4, k5, j6, i5, l5, k6);
			x2 += i7;
			x3 += i8;
			z2 += j7;
			z3 += j8;
			y3 += DrawingArea.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
	}

	private static void drawTexturedFogScanline(int[] dst,
			int[] src, int off, int x1, int x2, int z1, int z2, int l1, int i2,
			int j2, int k2, int l2, int i3) {
		int i = 0;// was parameter
		int j = 0;// was parameter
		if (x1 >= x2) {
			return;
		}
		z2 = (z2 - z1) / (x2 - x1);
		int k3;
		if (aBoolean1462) {
			if (x2 > DrawingArea.centerX) {
				x2 = DrawingArea.centerX;
			}
			if (x1 < 0) {
				z1 -= x1 * z2;
				x1 = 0;
			}
			if (x1 >= x2) {
				return;
			}
			k3 = x2 - x1 >> 3;
		} else {
			if (x2 - x1 > 7) {
				k3 = x2 - x1 >> 3;
			} else {
				k3 = 0;
			}
		}
		off += x1;
		if (lowMem) {
			int i4 = 0;
			int k4 = 0;
			int k6 = x1 - centerX;
			l1 += (k2 >> 3) * k6;
			i2 += (l2 >> 3) * k6;
			j2 += (i3 >> 3) * k6;
			int i5 = j2 >> 12;
			if (i5 != 0) {
				i = l1 / i5;
				j = i2 / i5;
				if (i < 0) {
					i = 0;
				} else if (i > 4032) {
					i = 4032;
				}
			}
			l1 += k2;
			i2 += l2;
			j2 += i3;
			i5 = j2 >> 12;
			if (i5 != 0) {
				i4 = l1 / i5;
				k4 = i2 / i5;
				if (i4 < 7) {
					i4 = 7;
				} else if (i4 > 4032) {
					i4 = 4032;
				}
			}
			int i7 = i4 - i >> 3;
			int k7 = k4 - j >> 3;
			while (k3-- > 0) {
				if (src[(j & 0xfc0) + (i >> 6)] != 0) {
					int z = z1 >> 16;
					if (z > FOG_BEGIN) {
						if (z >= FOG_END) {
							dst[off] = Client.currentFogColor;
						} else {
							int rgb = dst[off];
							int a1 = (z - FOG_BEGIN << 8)
									/ (FOG_END - FOG_BEGIN);
							int a2 = 256 - a1;
							int src2 = ((Client.currentFogColor & 0xff00ff)
									* a1 >> 8 & 0xff00ff)
									+ ((Client.currentFogColor & 0xff00) * a1 >> 8 & 0xff00);
							rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff)
									+ ((rgb & 0xff00) * a2 >> 8 & 0xff00);
							dst[off] = src2 + rgb;
						}
					}
				}
				off++;
				i += i7;
				j += k7;
				z1 += z2;
				if (src[(j & 0xfc0) + (i >> 6)] != 0) {
					int z = z1 >> 16;
					if (z > FOG_BEGIN) {
						if (z >= FOG_END) {
							dst[off] = Client.currentFogColor;
						} else {
							int rgb = dst[off];
							int a1 = (z - FOG_BEGIN << 8)
									/ (FOG_END - FOG_BEGIN);
							int a2 = 256 - a1;
							int src2 = ((Client.currentFogColor & 0xff00ff)
									* a1 >> 8 & 0xff00ff)
									+ ((Client.currentFogColor & 0xff00) * a1 >> 8 & 0xff00);
							rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff)
									+ ((rgb & 0xff00) * a2 >> 8 & 0xff00);
							dst[off] = src2 + rgb;
						}
					}
				}
				off++;
				i += i7;
				j += k7;
				z1 += z2;
				if (src[(j & 0xfc0) + (i >> 6)] != 0) {
					int z = z1 >> 16;
					if (z > FOG_BEGIN) {
						if (z >= FOG_END) {
							dst[off] = Client.currentFogColor;
						} else {
							int rgb = dst[off];
							int a1 = (z - FOG_BEGIN << 8)
									/ (FOG_END - FOG_BEGIN);
							int a2 = 256 - a1;
							int src2 = ((Client.currentFogColor & 0xff00ff)
									* a1 >> 8 & 0xff00ff)
									+ ((Client.currentFogColor & 0xff00) * a1 >> 8 & 0xff00);
							rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff)
									+ ((rgb & 0xff00) * a2 >> 8 & 0xff00);
							dst[off] = src2 + rgb;
						}
					}
				}
				off++;
				i += i7;
				j += k7;
				z1 += z2;
				if (src[(j & 0xfc0) + (i >> 6)] != 0) {
					int z = z1 >> 16;
					if (z > FOG_BEGIN) {
						if (z >= FOG_END) {
							dst[off] = Client.currentFogColor;
						} else {
							int rgb = dst[off];
							int a1 = (z - FOG_BEGIN << 8)
									/ (FOG_END - FOG_BEGIN);
							int a2 = 256 - a1;
							int src2 = ((Client.currentFogColor & 0xff00ff)
									* a1 >> 8 & 0xff00ff)
									+ ((Client.currentFogColor & 0xff00) * a1 >> 8 & 0xff00);
							rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff)
									+ ((rgb & 0xff00) * a2 >> 8 & 0xff00);
							dst[off] = src2 + rgb;
						}
					}
				}
				off++;
				i += i7;
				j += k7;
				z1 += z2;
				if (src[(j & 0xfc0) + (i >> 6)] != 0) {
					int z = z1 >> 16;
					if (z > FOG_BEGIN) {
						if (z >= FOG_END) {
							dst[off] = Client.currentFogColor;
						} else {
							int rgb = dst[off];
							int a1 = (z - FOG_BEGIN << 8)
									/ (FOG_END - FOG_BEGIN);
							int a2 = 256 - a1;
							int src2 = ((Client.currentFogColor & 0xff00ff)
									* a1 >> 8 & 0xff00ff)
									+ ((Client.currentFogColor & 0xff00) * a1 >> 8 & 0xff00);
							rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff)
									+ ((rgb & 0xff00) * a2 >> 8 & 0xff00);
							dst[off] = src2 + rgb;
						}
					}
				}
				off++;
				i += i7;
				j += k7;
				z1 += z2;
				if (src[(j & 0xfc0) + (i >> 6)] != 0) {
					int z = z1 >> 16;
					if (z > FOG_BEGIN) {
						if (z >= FOG_END) {
							dst[off] = Client.currentFogColor;
						} else {
							int rgb = dst[off];
							int a1 = (z - FOG_BEGIN << 8)
									/ (FOG_END - FOG_BEGIN);
							int a2 = 256 - a1;
							int src2 = ((Client.currentFogColor & 0xff00ff)
									* a1 >> 8 & 0xff00ff)
									+ ((Client.currentFogColor & 0xff00) * a1 >> 8 & 0xff00);
							rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff)
									+ ((rgb & 0xff00) * a2 >> 8 & 0xff00);
							dst[off] = src2 + rgb;
						}
					}
				}
				off++;
				i += i7;
				j += k7;
				z1 += z2;
				if (src[(j & 0xfc0) + (i >> 6)] != 0) {
					int z = z1 >> 16;
					if (z > FOG_BEGIN) {
						if (z >= FOG_END) {
							dst[off] = Client.currentFogColor;
						} else {
							int rgb = dst[off];
							int a1 = (z - FOG_BEGIN << 8)
									/ (FOG_END - FOG_BEGIN);
							int a2 = 256 - a1;
							int src2 = ((Client.currentFogColor & 0xff00ff)
									* a1 >> 8 & 0xff00ff)
									+ ((Client.currentFogColor & 0xff00) * a1 >> 8 & 0xff00);
							rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff)
									+ ((rgb & 0xff00) * a2 >> 8 & 0xff00);
							dst[off] = src2 + rgb;
						}
					}
				}
				off++;
				i += i7;
				j += k7;
				z1 += z2;
				if (src[(j & 0xfc0) + (i >> 6)] != 0) {
					int z = z1 >> 16;
					if (z > FOG_BEGIN) {
						if (z >= FOG_END) {
							dst[off] = Client.currentFogColor;
						} else {
							int rgb = dst[off];
							int a1 = (z - FOG_BEGIN << 8)
									/ (FOG_END - FOG_BEGIN);
							int a2 = 256 - a1;
							int src2 = ((Client.currentFogColor & 0xff00ff)
									* a1 >> 8 & 0xff00ff)
									+ ((Client.currentFogColor & 0xff00) * a1 >> 8 & 0xff00);
							rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff)
									+ ((rgb & 0xff00) * a2 >> 8 & 0xff00);
							dst[off] = src2 + rgb;
						}
					}
				}
				off++;
				i = i4;
				j = k4;
				z1 += z2;
				l1 += k2;
				i2 += l2;
				j2 += i3;
				int k5 = j2 >> 12;
				if (k5 != 0) {
					i4 = l1 / k5;
					k4 = i2 / k5;
					if (i4 < 7) {
						i4 = 7;
					} else if (i4 > 4032) {
						i4 = 4032;
					}
				}
				i7 = i4 - i >> 3;
				k7 = k4 - j >> 3;
			}
			for (k3 = x2 - x1 & 7; k3-- > 0;) {
				if (src[(j & 0xfc0) + (i >> 6)] != 0) {
					int z = z1 >> 16;
					if (z > FOG_BEGIN) {
						if (z >= FOG_END) {
							dst[off] = Client.currentFogColor;
						} else {
							int rgb = dst[off];
							int a1 = (z - FOG_BEGIN << 8)
									/ (FOG_END - FOG_BEGIN);
							int a2 = 256 - a1;
							int src2 = ((Client.currentFogColor & 0xff00ff)
									* a1 >> 8 & 0xff00ff)
									+ ((Client.currentFogColor & 0xff00) * a1 >> 8 & 0xff00);
							rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff)
									+ ((rgb & 0xff00) * a2 >> 8 & 0xff00);
							dst[off] = src2 + rgb;
						}
					}
				}
				off++;
				i += i7;
				j += k7;
				z1 += z2;
			}

			return;
		}
		int j4 = 0;
		int l4 = 0;
		int l6 = x1 - centerX;
		l1 += (k2 >> 3) * l6;
		i2 += (l2 >> 3) * l6;
		j2 += (i3 >> 3) * l6;
		int l5 = j2 >> 14;
		if (l5 != 0) {
			i = l1 / l5;
			j = i2 / l5;
			if (i < 0) {
				i = 0;
			} else if (i > 16256) {
				i = 16256;
			}
		}
		l1 += k2;
		i2 += l2;
		j2 += i3;
		l5 = j2 >> 14;
		if (l5 != 0) {
			j4 = l1 / l5;
			l4 = i2 / l5;
			if (j4 < 7) {
				j4 = 7;
			} else if (j4 > 16256) {
				j4 = 16256;
			}
		}
		int j7 = j4 - i >> 3;
		int l7 = l4 - j >> 3;
		while (k3-- > 0) {
			if (src[(j & 0x3f80) + (i >> 7)] != 0) {
				int z = z1 >> 16;
				if (z > FOG_BEGIN) {
					if (z >= FOG_END) {
						dst[off] = Client.currentFogColor;
					} else {
						int rgb = dst[off];
						int a1 = (z - FOG_BEGIN << 8) / (FOG_END - FOG_BEGIN);
						int a2 = 256 - a1;
						int src2 = ((Client.currentFogColor & 0xff00ff) * a1 >> 8 & 0xff00ff)
								+ ((Client.currentFogColor & 0xff00) * a1 >> 8 & 0xff00);
						rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff)
								+ ((rgb & 0xff00) * a2 >> 8 & 0xff00);
						dst[off] = src2 + rgb;
					}
				}
			}
			off++;
			i += j7;
			j += l7;
			z1 += z2;
			if (src[ (j & 0x3f80) + (i >> 7)] != 0) {
				int z = z1 >> 16;
				if (z > FOG_BEGIN) {
					if (z >= FOG_END) {
						dst[off] = Client.currentFogColor;
					} else {
						int rgb = dst[off];
						int a1 = (z - FOG_BEGIN << 8) / (FOG_END - FOG_BEGIN);
						int a2 = 256 - a1;
						int src2 = ((Client.currentFogColor & 0xff00ff) * a1 >> 8 & 0xff00ff)
								+ ((Client.currentFogColor & 0xff00) * a1 >> 8 & 0xff00);
						rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff)
								+ ((rgb & 0xff00) * a2 >> 8 & 0xff00);
						dst[off] = src2 + rgb;
					}
				}
			}
			off++;
			i += j7;
			j += l7;
			z1 += z2;
			if (src[ (j & 0x3f80) + (i >> 7)] != 0) {
				int z = z1 >> 16;
				if (z > FOG_BEGIN) {
					if (z >= FOG_END) {
						dst[off] = Client.currentFogColor;
					} else {
						int rgb = dst[off];
						int a1 = (z - FOG_BEGIN << 8) / (FOG_END - FOG_BEGIN);
						int a2 = 256 - a1;
						int src2 = ((Client.currentFogColor & 0xff00ff) * a1 >> 8 & 0xff00ff)
								+ ((Client.currentFogColor & 0xff00) * a1 >> 8 & 0xff00);
						rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff)
								+ ((rgb & 0xff00) * a2 >> 8 & 0xff00);
						dst[off] = src2 + rgb;
					}
				}
			}
			off++;
			i += j7;
			j += l7;
			z1 += z2;
			if (src[ (j & 0x3f80) + (i >> 7)] != 0) {
				int z = z1 >> 16;
				if (z > FOG_BEGIN) {
					if (z >= FOG_END) {
						dst[off] = Client.currentFogColor;
					} else {
						int rgb = dst[off];
						int a1 = (z - FOG_BEGIN << 8) / (FOG_END - FOG_BEGIN);
						int a2 = 256 - a1;
						int src2 = ((Client.currentFogColor & 0xff00ff) * a1 >> 8 & 0xff00ff)
								+ ((Client.currentFogColor & 0xff00) * a1 >> 8 & 0xff00);
						rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff)
								+ ((rgb & 0xff00) * a2 >> 8 & 0xff00);
						dst[off] = src2 + rgb;
					}
				}
			}
			off++;
			i += j7;
			j += l7;
			z1 += z2;
			if (src[(j & 0x3f80) + (i >> 7)] != 0) {
				int z = z1 >> 16;
				if (z > FOG_BEGIN) {
					if (z >= FOG_END) {
						dst[off] = Client.currentFogColor;
					} else {
						int rgb = dst[off];
						int a1 = (z - FOG_BEGIN << 8) / (FOG_END - FOG_BEGIN);
						int a2 = 256 - a1;
						int src2 = ((Client.currentFogColor & 0xff00ff) * a1 >> 8 & 0xff00ff)
								+ ((Client.currentFogColor & 0xff00) * a1 >> 8 & 0xff00);
						rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff)
								+ ((rgb & 0xff00) * a2 >> 8 & 0xff00);
						dst[off] = src2 + rgb;
					}
				}
			}
			off++;
			i += j7;
			j += l7;
			z1 += z2;
			if (src[(j & 0x3f80) + (i >> 7)] != 0) {
				int z = z1 >> 16;
				if (z > FOG_BEGIN) {
					if (z >= FOG_END) {
						dst[off] = Client.currentFogColor;
					} else {
						int rgb = dst[off];
						int a1 = (z - FOG_BEGIN << 8) / (FOG_END - FOG_BEGIN);
						int a2 = 256 - a1;
						int src2 = ((Client.currentFogColor & 0xff00ff) * a1 >> 8 & 0xff00ff)
								+ ((Client.currentFogColor & 0xff00) * a1 >> 8 & 0xff00);
						rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff)
								+ ((rgb & 0xff00) * a2 >> 8 & 0xff00);
						dst[off] = src2 + rgb;
					}
				}
			}
			off++;
			i += j7;
			j += l7;
			z1 += z2;
			if (src[ (j & 0x3f80) + (i >> 7)] != 0) {
				int z = z1 >> 16;
				if (z > FOG_BEGIN) {
					if (z >= FOG_END) {
						dst[off] = Client.currentFogColor;
					} else {
						int rgb = dst[off];
						int a1 = (z - FOG_BEGIN << 8) / (FOG_END - FOG_BEGIN);
						int a2 = 256 - a1;
						int src2 = ((Client.currentFogColor & 0xff00ff) * a1 >> 8 & 0xff00ff)
								+ ((Client.currentFogColor & 0xff00) * a1 >> 8 & 0xff00);
						rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff)
								+ ((rgb & 0xff00) * a2 >> 8 & 0xff00);
						dst[off] = src2 + rgb;
					}
				}
			}
			off++;
			i += j7;
			j += l7;
			z1 += z2;
			if (src[ (j & 0x3f80) + (i >> 7)] != 0) {
				int z = z1 >> 16;
				if (z > FOG_BEGIN) {
					if (z >= FOG_END) {
						dst[off] = Client.currentFogColor;
					} else {
						int rgb = dst[off];
						int a1 = (z - FOG_BEGIN << 8) / (FOG_END - FOG_BEGIN);
						int a2 = 256 - a1;
						int src2 = ((Client.currentFogColor & 0xff00ff) * a1 >> 8 & 0xff00ff)
								+ ((Client.currentFogColor & 0xff00) * a1 >> 8 & 0xff00);
						rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff)
								+ ((rgb & 0xff00) * a2 >> 8 & 0xff00);
						dst[off] = src2 + rgb;
					}
				}
			}
			off++;
			i = j4;
			j = l4;
			z1 += z2;
			l1 += k2;
			i2 += l2;
			j2 += i3;
			int j6 = j2 >> 14;
			if (j6 != 0) {
				j4 = l1 / j6;
				l4 = i2 / j6;
				if (j4 < 7) {
					j4 = 7;
				} else if (j4 > 16256) {
					j4 = 16256;
				}
			}
			j7 = j4 - i >> 3;
			l7 = l4 - j >> 3;
		}
		for (int l3 = x2 - x1 & 7; l3-- > 0;) {
			if (src[(j & 0x3f80) + (i >> 7)] != 0) {
				int z = z1 >> 16;
				if (z > FOG_BEGIN) {
					if (z >= FOG_END) {
						dst[off] = Client.currentFogColor;
					} else {
						int rgb = dst[off];
						int a1 = (z - FOG_BEGIN << 8) / (FOG_END - FOG_BEGIN);
						int a2 = 256 - a1;
						int src2 = ((Client.currentFogColor & 0xff00ff) * a1 >> 8 & 0xff00ff)
								+ ((Client.currentFogColor & 0xff00) * a1 >> 8 & 0xff00);
						rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff)
								+ ((rgb & 0xff00) * a2 >> 8 & 0xff00);
						dst[off] = src2 + rgb;
					}
				}
			}
			off++;
			i += j7;
			j += l7;
			z1 += z2;
		}
	}


	public static void method364()
	{
		lineOffsets = new int[DrawingArea.height];
		for(int j = 0; j < DrawingArea.height; j++)
			lineOffsets[j] = DrawingArea.width * j;

		centerX = DrawingArea.width / 2;
		centerY = DrawingArea.height / 2;
	}

	public static void method365(int j, int k)
	{
	   lineOffsets = new int[k];
		for(int l = 0; l < k; l++)
			lineOffsets[l] = j * l;

		centerX = j / 2;
		centerY = k / 2;
	}

	public static void method366()
	{
		anIntArrayArray1478 = null;
		for(int j = 0; j < textureAmount; j++)
			anIntArrayArray1479[j] = null;

	}

	public static void method367()
	{
		if(anIntArrayArray1478 == null)
		{
			anInt1477 = 20;//was parameter
			if(lowMem)
				anIntArrayArray1478 = new int[anInt1477][16384];
			else
				anIntArrayArray1478 = new int[anInt1477][0x10000];
			for(int k = 0; k < textureAmount; k++)
				anIntArrayArray1479[k] = null;

		}
	}

	public static void method368(StreamLoader streamLoader)
	{
		RendererLock.init();
		anInt1473 = 0;
		for(int j = 0; j < textureAmount; j++)
			try
			{
				aBackgroundArray1474s[j] = new Background(streamLoader, String.valueOf(j), 0);
				if(lowMem && aBackgroundArray1474s[j].anInt1456 == 128)
					aBackgroundArray1474s[j].method356();
				else
					aBackgroundArray1474s[j].method357();
				anInt1473++;
			}
			catch(Exception _ex) { }

	}

	public static int method369(int i)
	{
		if(anIntArray1476[i] != 0)
			return anIntArray1476[i];
		int k = 0;
		int l = 0;
		int i1 = 0;
		int j1 = anIntArrayArray1483[i].length;
		for(int k1 = 0; k1 < j1; k1++)
		{
			k += anIntArrayArray1483[i][k1] >> 16 & 0xff;
			l += anIntArrayArray1483[i][k1] >> 8 & 0xff;
			i1 += anIntArrayArray1483[i][k1] & 0xff;
		}

		int l1 = (k / j1 << 16) + (l / j1 << 8) + i1 / j1;
		l1 = method373(l1, 1.3999999999999999D);
		if(l1 == 0)
			l1 = 1;
		anIntArray1476[i] = l1;
		return l1;
	}

	public static void method370(int i)
	{
		if(anIntArrayArray1479[i] == null)
			return;
		anIntArrayArray1478[anInt1477++] = anIntArrayArray1479[i];
		anIntArrayArray1479[i] = null;
	}

	private static int[] method371(int i)
	{
		anIntArray1480[i] = anInt1481++;
		if(anIntArrayArray1479[i] != null)
			return anIntArrayArray1479[i];
		int ai[];
		if(anInt1477 > 0)
		{
			ai = anIntArrayArray1478[--anInt1477];
			anIntArrayArray1478[anInt1477] = null;
		} else
		{
			int j = 0;
			int k = -1;
			for(int l = 0; l < anInt1473; l++)
				if(anIntArrayArray1479[l] != null && (anIntArray1480[l] < j || k == -1))
				{
					j = anIntArray1480[l];
					k = l;
				}

			ai = anIntArrayArray1479[k];
			anIntArrayArray1479[k] = null;
		}
		anIntArrayArray1479[i] = ai;
		Background background = aBackgroundArray1474s[i];
		int ai1[] = anIntArrayArray1483[i];
		if(lowMem)
		{
			aBooleanArray1475[i] = false;
			for(int i1 = 0; i1 < 4096; i1++)
			{
				int i2 = ai[i1] = ai1[background.aByteArray1450[i1]] & 0xf8f8ff;
				if(i2 == 0)
					aBooleanArray1475[i] = true;
				ai[4096 + i1] = i2 - (i2 >>> 3) & 0xf8f8ff;
				ai[8192 + i1] = i2 - (i2 >>> 2) & 0xf8f8ff;
				ai[12288 + i1] = i2 - (i2 >>> 2) - (i2 >>> 3) & 0xf8f8ff;
			}

		} else
		{
			if(background.anInt1452 == 64)
			{
				for(int j1 = 0; j1 < 128; j1++)
				{
					for(int j2 = 0; j2 < 128; j2++)
						ai[j2 + (j1 << 7)] = ai1[background.aByteArray1450[(j2 >> 1) + ((j1 >> 1) << 6)]];

				}

			} else
			{
				for(int k1 = 0; k1 < 16384; k1++)
					ai[k1] = ai1[background.aByteArray1450[k1]];

			}
			aBooleanArray1475[i] = false;
			for(int l1 = 0; l1 < 16384; l1++)
			{
				ai[l1] &= 0xf8f8ff;
				int k2 = ai[l1];
				if(k2 == 0)
					aBooleanArray1475[i] = true;
				ai[16384 + l1] = k2 - (k2 >>> 3) & 0xf8f8ff;
				ai[32768 + l1] = k2 - (k2 >>> 2) & 0xf8f8ff;
				ai[49152 + l1] = k2 - (k2 >>> 2) - (k2 >>> 3) & 0xf8f8ff;
			}

		}
		return ai;
	}

	public static void method372(double d)
	{
		d += Math.random() * 0.029999999999999999D - 0.014999999999999999D;
		int j = 0;
		for(int k = 0; k < 512; k++)
		{
			double d1 = (double)(k / 8) / 64D + 0.0078125D;
			double d2 = (double)(k & 7) / 8D + 0.0625D;
			for(int k1 = 0; k1 < 128; k1++)
			{
				double d3 = (double)k1 / 128D;
				double d4 = d3;
				double d5 = d3;
				double d6 = d3;
				if(d2 != 0.0D)
				{
					double d7;
					if(d3 < 0.5D)
						d7 = d3 * (1.0D + d2);
					else
						d7 = (d3 + d2) - d3 * d2;
					double d8 = 2D * d3 - d7;
					double d9 = d1 + 0.33333333333333331D;
					if(d9 > 1.0D)
						d9--;
					double d10 = d1;
					double d11 = d1 - 0.33333333333333331D;
					if(d11 < 0.0D)
						d11++;
					if(6D * d9 < 1.0D)
						d4 = d8 + (d7 - d8) * 6D * d9;
					else
					if(2D * d9 < 1.0D)
						d4 = d7;
					else
					if(3D * d9 < 2D)
						d4 = d8 + (d7 - d8) * (0.66666666666666663D - d9) * 6D;
					else
						d4 = d8;
					if(6D * d10 < 1.0D)
						d5 = d8 + (d7 - d8) * 6D * d10;
					else
					if(2D * d10 < 1.0D)
						d5 = d7;
					else
					if(3D * d10 < 2D)
						d5 = d8 + (d7 - d8) * (0.66666666666666663D - d10) * 6D;
					else
						d5 = d8;
					if(6D * d11 < 1.0D)
						d6 = d8 + (d7 - d8) * 6D * d11;
					else
					if(2D * d11 < 1.0D)
						d6 = d7;
					else
					if(3D * d11 < 2D)
						d6 = d8 + (d7 - d8) * (0.66666666666666663D - d11) * 6D;
					else
						d6 = d8;
				}
				int l1 = (int)(d4 * 256D);
				int i2 = (int)(d5 * 256D);
				int j2 = (int)(d6 * 256D);
				int k2 = (l1 << 16) + (i2 << 8) + j2;
				k2 = method373(k2, d);
				if(k2 == 0)
					k2 = 1;
				anIntArray1482[j++] = k2;
			}

		}

		for(int l = 0; l < textureAmount; l++)
			if(aBackgroundArray1474s[l] != null)
			{
				int ai[] = aBackgroundArray1474s[l].anIntArray1451;
				anIntArrayArray1483[l] = new int[ai.length];
				for(int j1 = 0; j1 < ai.length; j1++)
				{
					anIntArrayArray1483[l][j1] = method373(ai[j1], d);
					if((anIntArrayArray1483[l][j1] & 0xf8f8ff) == 0 && j1 != 0)
						anIntArrayArray1483[l][j1] = 1;
				}

			}

		for(int i1 = 0; i1 < textureAmount; i1++)
			method370(i1);

	}

	private static int method373(int i, double d)
	{
		double d1 = (double)(i >> 16) / 256D;
		double d2 = (double)(i >> 8 & 0xff) / 256D;
		double d3 = (double)(i & 0xff) / 256D;
		d1 = Math.pow(d1, d);
		d2 = Math.pow(d2, d);
		d3 = Math.pow(d3, d);
		int j = (int)(d1 * 256D);
		int k = (int)(d2 * 256D);
		int l = (int)(d3 * 256D);
		return (j << 16) + (k << 8) + l;
	}

	public static void drawGouraudTriangle(final int y1, final int y2, final int y3, final int x1, final int x2, final int x3, final int hsl1, final int hsl2, final int hsl3) {
		if (RendererLock.active) {
			RendererLock.submit(new Runnable() {

				@Override
				public void run() {
					if (Configuration.smoothShading && aBoolean1464) {
						drawHDGouraudTriangle(y1, y2, y3, x1, x2, x3, hsl1, hsl2, hsl3);
					} else {
						drawLDGouraudTriangle(y1, y2, y3, x1, x2, x3, hsl1, hsl2, hsl3);
					}
					RendererLock.completed();
				}
			});
		} else {
			if (Configuration.smoothShading && aBoolean1464) {
				drawHDGouraudTriangle(y1, y2, y3, x1, x2, x3, hsl1, hsl2, hsl3);
			} else {
				drawLDGouraudTriangle(y1, y2, y3, x1, x2, x3, hsl1, hsl2, hsl3);
			}
		}
	}

	
	public static void drawLDGouraudTriangle(int y1, int y2, int y3, int x1, int x2, int x3, int hsl1, int hsl2, int hsl3) {
		int j2 = 0;
		int k2 = 0;
		if(y2 != y1)
		{
			j2 = (x2 - x1 << 16) / (y2 - y1);
			k2 = (hsl2 - hsl1 << 15) / (y2 - y1);
		}
		int l2 = 0;
		int i3 = 0;
		if(y3 != y2)
		{
			l2 = (x3 - x2 << 16) / (y3 - y2);
			i3 = (hsl3 - hsl2 << 15) / (y3 - y2);
		}
		int j3 = 0;
		int k3 = 0;
		if(y3 != y1)
		{
			j3 = (x1 - x3 << 16) / (y1 - y3);
			k3 = (hsl1 - hsl3 << 15) / (y1 - y3);
		}
		if(y1 <= y2 && y1 <= y3)
		{
			if(y1 >= DrawingArea.bottomY)
				return;
			if(y2 > DrawingArea.bottomY)
				y2 = DrawingArea.bottomY;
			if(y3 > DrawingArea.bottomY)
				y3 = DrawingArea.bottomY;
			if(y2 < y3)
			{
				x3 = x1 <<= 16;
				hsl3 = hsl1 <<= 15;
				if(y1 < 0)
				{
					x3 -= j3 * y1;
					x1 -= j2 * y1;
					hsl3 -= k3 * y1;
					hsl1 -= k2 * y1;
					y1 = 0;
				}
				x2 <<= 16;
				hsl2 <<= 15;
				if(y2 < 0)
				{
					x2 -= l2 * y2;
					hsl2 -= i3 * y2;
					y2 = 0;
				}
				if(y1 != y2 && j3 < j2 || y1 == y2 && j3 > l2)
				{
					y3 -= y2;
					y2 -= y1;
					for(y1 = lineOffsets[y1]; --y2 >= 0; y1 += DrawingArea.width)
					{
						drawLDGouraudScanline(DrawingArea.pixels, y1, x3 >> 16, x1 >> 16, hsl3 >> 7, hsl1 >> 7);
						x3 += j3;
						x1 += j2;
						hsl3 += k3;
						hsl1 += k2;
					}

					while(--y3 >= 0) 
					{
						drawLDGouraudScanline(DrawingArea.pixels, y1, x3 >> 16, x2 >> 16, hsl3 >> 7, hsl2 >> 7);
						x3 += j3;
						x2 += l2;
						hsl3 += k3;
						hsl2 += i3;
						y1 += DrawingArea.width;
					}
					return;
				}
				y3 -= y2;
				y2 -= y1;
				for(y1 = lineOffsets[y1]; --y2 >= 0; y1 += DrawingArea.width)
				{
					drawLDGouraudScanline(DrawingArea.pixels, y1, x1 >> 16, x3 >> 16, hsl1 >> 7, hsl3 >> 7);
					x3 += j3;
					x1 += j2;
					hsl3 += k3;
					hsl1 += k2;
				}

				while(--y3 >= 0) 
				{
					drawLDGouraudScanline(DrawingArea.pixels, y1, x2 >> 16, x3 >> 16, hsl2 >> 7, hsl3 >> 7);
					x3 += j3;
					x2 += l2;
					hsl3 += k3;
					hsl2 += i3;
					y1 += DrawingArea.width;
				}
				return;
			}
			x2 = x1 <<= 16;
			hsl2 = hsl1 <<= 15;
			if(y1 < 0)
			{
				x2 -= j3 * y1;
				x1 -= j2 * y1;
				hsl2 -= k3 * y1;
				hsl1 -= k2 * y1;
				y1 = 0;
			}
			x3 <<= 16;
			hsl3 <<= 15;
			if(y3 < 0)
			{
				x3 -= l2 * y3;
				hsl3 -= i3 * y3;
				y3 = 0;
			}
			if(y1 != y3 && j3 < j2 || y1 == y3 && l2 > j2)
			{
				y2 -= y3;
				y3 -= y1;
				for(y1 = lineOffsets[y1]; --y3 >= 0; y1 += DrawingArea.width)
				{
					drawLDGouraudScanline(DrawingArea.pixels, y1, x2 >> 16, x1 >> 16, hsl2 >> 7, hsl1 >> 7);
					x2 += j3;
					x1 += j2;
					hsl2 += k3;
					hsl1 += k2;
				}

				while(--y2 >= 0) 
				{
					drawLDGouraudScanline(DrawingArea.pixels, y1, x3 >> 16, x1 >> 16, hsl3 >> 7, hsl1 >> 7);
					x3 += l2;
					x1 += j2;
					hsl3 += i3;
					hsl1 += k2;
					y1 += DrawingArea.width;
				}
				return;
			}
			y2 -= y3;
			y3 -= y1;
			for(y1 = lineOffsets[y1]; --y3 >= 0; y1 += DrawingArea.width)
			{
				drawLDGouraudScanline(DrawingArea.pixels, y1, x1 >> 16, x2 >> 16, hsl1 >> 7, hsl2 >> 7);
				x2 += j3;
				x1 += j2;
				hsl2 += k3;
				hsl1 += k2;
			}

			while(--y2 >= 0) 
			{
				drawLDGouraudScanline(DrawingArea.pixels, y1, x1 >> 16, x3 >> 16, hsl1 >> 7, hsl3 >> 7);
				x3 += l2;
				x1 += j2;
				hsl3 += i3;
				hsl1 += k2;
				y1 += DrawingArea.width;
			}
			return;
		}
		if(y2 <= y3)
		{
			if(y2 >= DrawingArea.bottomY)
				return;
			if(y3 > DrawingArea.bottomY)
				y3 = DrawingArea.bottomY;
			if(y1 > DrawingArea.bottomY)
				y1 = DrawingArea.bottomY;
			if(y3 < y1)
			{
				x1 = x2 <<= 16;
				hsl1 = hsl2 <<= 15;
				if(y2 < 0)
				{
					x1 -= j2 * y2;
					x2 -= l2 * y2;
					hsl1 -= k2 * y2;
					hsl2 -= i3 * y2;
					y2 = 0;
				}
				x3 <<= 16;
				hsl3 <<= 15;
				if(y3 < 0)
				{
					x3 -= j3 * y3;
					hsl3 -= k3 * y3;
					y3 = 0;
				}
				if(y2 != y3 && j2 < l2 || y2 == y3 && j2 > j3)
				{
					y1 -= y3;
					y3 -= y2;
					for(y2 = lineOffsets[y2]; --y3 >= 0; y2 += DrawingArea.width)
					{
						drawLDGouraudScanline(DrawingArea.pixels, y2, x1 >> 16, x2 >> 16, hsl1 >> 7, hsl2 >> 7);
						x1 += j2;
						x2 += l2;
						hsl1 += k2;
						hsl2 += i3;
					}

					while(--y1 >= 0) 
					{
						drawLDGouraudScanline(DrawingArea.pixels, y2, x1 >> 16, x3 >> 16, hsl1 >> 7, hsl3 >> 7);
						x1 += j2;
						x3 += j3;
						hsl1 += k2;
						hsl3 += k3;
						y2 += DrawingArea.width;
					}
					return;
				}
				y1 -= y3;
				y3 -= y2;
				for(y2 = lineOffsets[y2]; --y3 >= 0; y2 += DrawingArea.width)
				{
					drawLDGouraudScanline(DrawingArea.pixels, y2, x2 >> 16, x1 >> 16, hsl2 >> 7, hsl1 >> 7);
					x1 += j2;
					x2 += l2;
					hsl1 += k2;
					hsl2 += i3;
				}

				while(--y1 >= 0) 
				{
					drawLDGouraudScanline(DrawingArea.pixels, y2, x3 >> 16, x1 >> 16, hsl3 >> 7, hsl1 >> 7);
					x1 += j2;
					x3 += j3;
					hsl1 += k2;
					hsl3 += k3;
					y2 += DrawingArea.width;
				}
				return;
			}
			x3 = x2 <<= 16;
			hsl3 = hsl2 <<= 15;
			if(y2 < 0)
			{
				x3 -= j2 * y2;
				x2 -= l2 * y2;
				hsl3 -= k2 * y2;
				hsl2 -= i3 * y2;
				y2 = 0;
			}
			x1 <<= 16;
			hsl1 <<= 15;
			if(y1 < 0)
			{
				x1 -= j3 * y1;
				hsl1 -= k3 * y1;
				y1 = 0;
			}
			if(j2 < l2)
			{
				y3 -= y1;
				y1 -= y2;
				for(y2 = lineOffsets[y2]; --y1 >= 0; y2 += DrawingArea.width)
				{
					drawLDGouraudScanline(DrawingArea.pixels, y2, x3 >> 16, x2 >> 16, hsl3 >> 7, hsl2 >> 7);
					x3 += j2;
					x2 += l2;
					hsl3 += k2;
					hsl2 += i3;
				}

				while(--y3 >= 0) 
				{
					drawLDGouraudScanline(DrawingArea.pixels, y2, x1 >> 16, x2 >> 16, hsl1 >> 7, hsl2 >> 7);
					x1 += j3;
					x2 += l2;
					hsl1 += k3;
					hsl2 += i3;
					y2 += DrawingArea.width;
				}
				return;
			}
			y3 -= y1;
			y1 -= y2;
			for(y2 = lineOffsets[y2]; --y1 >= 0; y2 += DrawingArea.width)
			{
				drawLDGouraudScanline(DrawingArea.pixels, y2, x2 >> 16, x3 >> 16, hsl2 >> 7, hsl3 >> 7);
				x3 += j2;
				x2 += l2;
				hsl3 += k2;
				hsl2 += i3;
			}

			while(--y3 >= 0) 
			{
				drawLDGouraudScanline(DrawingArea.pixels, y2, x2 >> 16, x1 >> 16, hsl2 >> 7, hsl1 >> 7);
				x1 += j3;
				x2 += l2;
				hsl1 += k3;
				hsl2 += i3;
				y2 += DrawingArea.width;
			}
			return;
		}
		if(y3 >= DrawingArea.bottomY)
			return;
		if(y1 > DrawingArea.bottomY)
			y1 = DrawingArea.bottomY;
		if(y2 > DrawingArea.bottomY)
			y2 = DrawingArea.bottomY;
		if(y1 < y2)
		{
			x2 = x3 <<= 16;
			hsl2 = hsl3 <<= 15;
			if(y3 < 0)
			{
				x2 -= l2 * y3;
				x3 -= j3 * y3;
				hsl2 -= i3 * y3;
				hsl3 -= k3 * y3;
				y3 = 0;
			}
			x1 <<= 16;
			hsl1 <<= 15;
			if(y1 < 0)
			{
				x1 -= j2 * y1;
				hsl1 -= k2 * y1;
				y1 = 0;
			}
			if(l2 < j3)
			{
				y2 -= y1;
				y1 -= y3;
				for(y3 = lineOffsets[y3]; --y1 >= 0; y3 += DrawingArea.width)
				{
					drawLDGouraudScanline(DrawingArea.pixels, y3, x2 >> 16, x3 >> 16, hsl2 >> 7, hsl3 >> 7);
					x2 += l2;
					x3 += j3;
					hsl2 += i3;
					hsl3 += k3;
				}

				while(--y2 >= 0) 
				{
					drawLDGouraudScanline(DrawingArea.pixels, y3, x2 >> 16, x1 >> 16, hsl2 >> 7, hsl1 >> 7);
					x2 += l2;
					x1 += j2;
					hsl2 += i3;
					hsl1 += k2;
					y3 += DrawingArea.width;
				}
				return;
			}
			y2 -= y1;
			y1 -= y3;
			for(y3 = lineOffsets[y3]; --y1 >= 0; y3 += DrawingArea.width)
			{
				drawLDGouraudScanline(DrawingArea.pixels, y3, x3 >> 16, x2 >> 16, hsl3 >> 7, hsl2 >> 7);
				x2 += l2;
				x3 += j3;
				hsl2 += i3;
				hsl3 += k3;
			}

			while(--y2 >= 0) 
			{
				drawLDGouraudScanline(DrawingArea.pixels, y3, x1 >> 16, x2 >> 16, hsl1 >> 7, hsl2 >> 7);
				x2 += l2;
				x1 += j2;
				hsl2 += i3;
				hsl1 += k2;
				y3 += DrawingArea.width;
			}
			return;
		}
		x1 = x3 <<= 16;
		hsl1 = hsl3 <<= 15;
		if(y3 < 0)
		{
			x1 -= l2 * y3;
			x3 -= j3 * y3;
			hsl1 -= i3 * y3;
			hsl3 -= k3 * y3;
			y3 = 0;
		}
		x2 <<= 16;
		hsl2 <<= 15;
		if(y2 < 0)
		{
			x2 -= j2 * y2;
			hsl2 -= k2 * y2;
			y2 = 0;
		}
		if(l2 < j3)
		{
			y1 -= y2;
			y2 -= y3;
			for(y3 = lineOffsets[y3]; --y2 >= 0; y3 += DrawingArea.width)
			{
				drawLDGouraudScanline(DrawingArea.pixels, y3, x1 >> 16, x3 >> 16, hsl1 >> 7, hsl3 >> 7);
				x1 += l2;
				x3 += j3;
				hsl1 += i3;
				hsl3 += k3;
			}

			while(--y1 >= 0) 
			{
				drawLDGouraudScanline(DrawingArea.pixels, y3, x2 >> 16, x3 >> 16, hsl2 >> 7, hsl3 >> 7);
				x2 += j2;
				x3 += j3;
				hsl2 += k2;
				hsl3 += k3;
				y3 += DrawingArea.width;
			}
			return;
		}
		y1 -= y2;
		y2 -= y3;
		for(y3 = lineOffsets[y3]; --y2 >= 0; y3 += DrawingArea.width)
		{
			drawLDGouraudScanline(DrawingArea.pixels, y3, x3 >> 16, x1 >> 16, hsl3 >> 7, hsl1 >> 7);
			x1 += l2;
			x3 += j3;
			hsl1 += i3;
			hsl3 += k3;
		}

		while(--y1 >= 0) 
		{
			drawLDGouraudScanline(DrawingArea.pixels, y3, x3 >> 16, x2 >> 16, hsl3 >> 7, hsl2 >> 7);
			x2 += j2;
			x3 += j3;
			hsl2 += k2;
			hsl3 += k3;
			y3 += DrawingArea.width;
		}
	}

	private static void drawLDGouraudScanline(int ai[], int i, int l, int i1, int j1, int k1)
	{
		int j;//was parameter
		int k;//was parameter
		if(aBoolean1464)
		{
			int l1;
			if(aBoolean1462)
			{
				if(i1 - l > 3)
					l1 = (k1 - j1) / (i1 - l);
				else
					l1 = 0;
				if(i1 > DrawingArea.centerX)
					i1 = DrawingArea.centerX;
				if(l < 0)
				{
					j1 -= l * l1;
					l = 0;
				}
				if(l >= i1)
					return;
				i += l;
				k = i1 - l >> 2;
				l1 <<= 2;
			} else
			{
				if(l >= i1)
					return;
				i += l;
				k = i1 - l >> 2;
				if(k > 0)
					l1 = (k1 - j1) * anIntArray1468[k] >> 15;
				else
					l1 = 0;
			}
			if(anInt1465 == 0)
			{
				while(--k >= 0) 
				{
					j = anIntArray1482[j1 >> 8];
					j1 += l1;
					ai[i++] = j;
					ai[i++] = j;
					ai[i++] = j;
					ai[i++] = j;
				}
				k = i1 - l & 3;
				if(k > 0)
				{
					j = anIntArray1482[j1 >> 8];
					do
						ai[i++] = j;
					while(--k > 0);
					return;
				}
			} else
			{
				int j2 = anInt1465;
				int l2 = 256 - anInt1465;
				while(--k >= 0) 
				{
					j = anIntArray1482[j1 >> 8];
					j1 += l1;
					j = ((j & 0xff00ff) * l2 >> 8 & 0xff00ff) + ((j & 0xff00) * l2 >> 8 & 0xff00);
					ai[i++] = j + ((ai[i] & 0xff00ff) * j2 >> 8 & 0xff00ff) + ((ai[i] & 0xff00) * j2 >> 8 & 0xff00);
					ai[i++] = j + ((ai[i] & 0xff00ff) * j2 >> 8 & 0xff00ff) + ((ai[i] & 0xff00) * j2 >> 8 & 0xff00);
					ai[i++] = j + ((ai[i] & 0xff00ff) * j2 >> 8 & 0xff00ff) + ((ai[i] & 0xff00) * j2 >> 8 & 0xff00);
					ai[i++] = j + ((ai[i] & 0xff00ff) * j2 >> 8 & 0xff00ff) + ((ai[i] & 0xff00) * j2 >> 8 & 0xff00);
				}
				k = i1 - l & 3;
				if(k > 0)
				{
					j = anIntArray1482[j1 >> 8];
					j = ((j & 0xff00ff) * l2 >> 8 & 0xff00ff) + ((j & 0xff00) * l2 >> 8 & 0xff00);
					do
						ai[i++] = j + ((ai[i] & 0xff00ff) * j2 >> 8 & 0xff00ff) + ((ai[i] & 0xff00) * j2 >> 8 & 0xff00);
					while(--k > 0);
				}
			}
			return;
		}
		if(l >= i1)
			return;
		int i2 = (k1 - j1) / (i1 - l);
		if(aBoolean1462)
		{
			if(i1 > DrawingArea.centerX)
				i1 = DrawingArea.centerX;
			if(l < 0)
			{
				j1 -= l * i2;
				l = 0;
			}
			if(l >= i1)
				return;
		}
		i += l;
		k = i1 - l;
		if(anInt1465 == 0)
		{
			do
			{
				ai[i++] = anIntArray1482[j1 >> 8];
				j1 += i2;
			} while(--k > 0);
			return;
		}
		int k2 = anInt1465;
		int i3 = 256 - anInt1465;
		do
		{
			j = anIntArray1482[j1 >> 8];
			j1 += i2;
			j = ((j & 0xff00ff) * i3 >> 8 & 0xff00ff) + ((j & 0xff00) * i3 >> 8 & 0xff00);
			ai[i++] = j + ((ai[i] & 0xff00ff) * k2 >> 8 & 0xff00ff) + ((ai[i] & 0xff00) * k2 >> 8 & 0xff00);
		} while(--k > 0);
	}

	public static void drawHDGouraudTriangle(int y1, int y2, int y3, int x1, int x2, int x3, int hsl1, int hsl2, int hsl3) {
		int rgb1 = anIntArray1482[hsl1];
		int rgb2 = anIntArray1482[hsl2];
		int rgb3 = anIntArray1482[hsl3];
		int r1 = rgb1 >> 16 & 0xff;
		int g1 = rgb1 >> 8 & 0xff;
		int b1 = rgb1 & 0xff;
		int r2 = rgb2 >> 16 & 0xff;
		int g2 = rgb2 >> 8 & 0xff;
		int b2 = rgb2 & 0xff;
		int r3 = rgb3 >> 16 & 0xff;
		int g3 = rgb3 >> 8 & 0xff;
		int b3 = rgb3 & 0xff;
		int dx1 = 0;
		int dr1 = 0;
		int dg1 = 0;
		int db1 = 0;
		if (y2 != y1) {
			dx1 = (x2 - x1 << 16) / (y2 - y1);
			dr1 = (r2 - r1 << 16) / (y2 - y1);
			dg1 = (g2 - g1 << 16) / (y2 - y1);
			db1 = (b2 - b1 << 16) / (y2 - y1);
		}
		int dx2 = 0;
		int dr2 = 0;
		int dg2 = 0;
		int db2 = 0;
		if (y3 != y2) {
			dx2 = (x3 - x2 << 16) / (y3 - y2);
			dr2 = (r3 - r2 << 16) / (y3 - y2);
			dg2 = (g3 - g2 << 16) / (y3 - y2);
			db2 = (b3 - b2 << 16) / (y3 - y2);
		}
		int dx3 = 0;
		int dr3 = 0;
		int dg3 = 0;
		int db3 = 0;
		if (y3 != y1) {
			dx3 = (x1 - x3 << 16) / (y1 - y3);
			dr3 = (r1 - r3 << 16) / (y1 - y3);
			dg3 = (g1 - g3 << 16) / (y1 - y3);
			db3 = (b1 - b3 << 16) / (y1 - y3);
		}
		if(y1 <= y2 && y1 <= y3) {
			if(y1 >= DrawingArea.bottomY) {
				return;
			}
			if(y2 > DrawingArea.bottomY) {
				y2 = DrawingArea.bottomY;
			}
			if(y3 > DrawingArea.bottomY) {
				y3 = DrawingArea.bottomY;
			}
			if(y2 < y3) {
				x3 = x1 <<= 16;
				r3 = r1 <<= 16;
				g3 = g1 <<= 16;
				b3 = b1 <<= 16;
				if(y1 < 0) {
					x3 -= dx3 * y1;
					x1 -= dx1 * y1;
					r3 -= dr3 * y1;
					g3 -= dg3 * y1;
					b3 -= db3 * y1;
					r1 -= dr1 * y1;
					g1 -= dg1 * y1;
					b1 -= db1 * y1;
					y1 = 0;
				}
				x2 <<= 16;
				r2 <<= 16;
				g2 <<= 16;
				b2 <<= 16;
				if(y2 < 0) {
					x2 -= dx2 * y2;
					r2 -= dr2 * y2;
					g2 -= dg2 * y2;
					b2 -= db2 * y2;
					y2 = 0;
				}
				if(y1 != y2 && dx3 < dx1 || y1 == y2 && dx3 > dx2) {
					y3 -= y2;
					y2 -= y1;
					for(y1 = lineOffsets[y1]; --y2 >= 0; y1 += DrawingArea.width) {
						drawHDGouraudScanline(DrawingArea.pixels, y1, x3 >> 16, x1 >> 16, r3, g3, b3, r1, g1, b1);
						x3 += dx3;
						x1 += dx1;
						r3 += dr3;
						g3 += dg3;
						b3 += db3;
						r1 += dr1;
						g1 += dg1;
						b1 += db1;
					}
					while(--y3 >= 0) {
						drawHDGouraudScanline(DrawingArea.pixels, y1, x3 >> 16, x2 >> 16, r3, g3, b3, r2, g2, b2);
						x3 += dx3;
						x2 += dx2;
						r3 += dr3;
						g3 += dg3;
						b3 += db3;
						r2 += dr2;
						g2 += dg2;
						b2 += db2;
						y1 += DrawingArea.width;
					}
					return;
				}
				y3 -= y2;
				y2 -= y1;
				for(y1 = lineOffsets[y1]; --y2 >= 0; y1 += DrawingArea.width) {
					drawHDGouraudScanline(DrawingArea.pixels, y1, x1 >> 16, x3 >> 16, r1, g1, b1, r3, g3, b3);
					x3 += dx3;
					x1 += dx1;
					r3 += dr3;
					g3 += dg3;
					b3 += db3;
					r1 += dr1;
					g1 += dg1;
					b1 += db1;
				}
				while(--y3 >= 0) {
					drawHDGouraudScanline(DrawingArea.pixels, y1, x2 >> 16, x3 >> 16, r2, g2, b2, r3, g3, b3);
					x3 += dx3;
					x2 += dx2;
					r3 += dr3;
					g3 += dg3;
					b3 += db3;
					r2 += dr2;
					g2 += dg2;
					b2 += db2;
					y1 += DrawingArea.width;
				}
				return;
			}
			x2 = x1 <<= 16;
			r2 = r1 <<= 16;
			g2 = g1 <<= 16;
			b2 = b1 <<= 16;
			if(y1 < 0) {
				x2 -= dx3 * y1;
				x1 -= dx1 * y1;
				r2 -= dr3 * y1;
				g2 -= dg3 * y1;
				b2 -= db3 * y1;
				r1 -= dr1 * y1;
				g1 -= dg1 * y1;
				b1 -= db1 * y1;
				y1 = 0;
			}
			x3 <<= 16;
			r3 <<= 16;
			g3 <<= 16;
			b3 <<= 16;
			if(y3 < 0) {
				x3 -= dx2 * y3;
				r3 -= dr2 * y3;
				g3 -= dg2 * y3;
				b3 -= db2 * y3;
				y3 = 0;
			}
			if(y1 != y3 && dx3 < dx1 || y1 == y3 && dx2 > dx1) {
				y2 -= y3;
				y3 -= y1;
				for(y1 = lineOffsets[y1]; --y3 >= 0; y1 += DrawingArea.width) {
					drawHDGouraudScanline(DrawingArea.pixels, y1, x2 >> 16, x1 >> 16, r2, g2, b2, r1, g1, b1);
					x2 += dx3;
					x1 += dx1;
					r2 += dr3;
					g2 += dg3;
					b2 += db3;
					r1 += dr1;
					g1 += dg1;
					b1 += db1;
				}
				while(--y2 >= 0) {
					drawHDGouraudScanline(DrawingArea.pixels, y1, x3 >> 16, x1 >> 16, r3, g3, b3, r1, g1, b1);
					x3 += dx2;
					x1 += dx1;
					r3 += dr2;
					g3 += dg2;
					b3 += db2;
					r1 += dr1;
					g1 += dg1;
					b1 += db1;
					y1 += DrawingArea.width;
				}
				return;
			}
			y2 -= y3;
			y3 -= y1;
			for(y1 = lineOffsets[y1]; --y3 >= 0; y1 += DrawingArea.width) {
				drawHDGouraudScanline(DrawingArea.pixels, y1, x1 >> 16, x2 >> 16, r1, g1, b1, r2, g2, b2);
				x2 += dx3;
				x1 += dx1;
				r2 += dr3;
				g2 += dg3;
				b2 += db3;
				r1 += dr1;
				g1 += dg1;
				b1 += db1;
			}
			while(--y2 >= 0) {
				drawHDGouraudScanline(DrawingArea.pixels, y1, x1 >> 16, x3 >> 16, r1, g1, b1, r3, g3, b3);
				x3 += dx2;
				x1 += dx1;
				r3 += dr2;
				g3 += dg2;
				b3 += db2;
				r1 += dr1;
				g1 += dg1;
				b1 += db1;
				y1 += DrawingArea.width;
			}
			return;
		}
		if(y2 <= y3) {
			if(y2 >= DrawingArea.bottomY) {
				return;
			}
			if(y3 > DrawingArea.bottomY) {
				y3 = DrawingArea.bottomY;
			}
			if(y1 > DrawingArea.bottomY) {
				y1 = DrawingArea.bottomY;
			}
			if(y3 < y1) {
				x1 = x2 <<= 16;
				r1 = r2 <<= 16;
				g1 = g2 <<= 16;
				b1 = b2 <<= 16;
				if(y2 < 0) {
					x1 -= dx1 * y2;
					x2 -= dx2 * y2;
					r1 -= dr1 * y2;
					g1 -= dg1 * y2;
					b1 -= db1 * y2;
					r2 -= dr2 * y2;
					g2 -= dg2 * y2;
					b2 -= db2 * y2;
					y2 = 0;
				}
				x3 <<= 16;
				r3 <<= 16;
				g3 <<= 16;
				b3 <<= 16;
				if(y3 < 0) {
					x3 -= dx3 * y3;
					r3 -= dr3 * y3;
					g3 -= dg3 * y3;
					b3 -= db3 * y3;
					y3 = 0;
				}
				if(y2 != y3 && dx1 < dx2 || y2 == y3 && dx1 > dx3) {
					y1 -= y3;
					y3 -= y2;
					for(y2 = lineOffsets[y2]; --y3 >= 0; y2 += DrawingArea.width) {
						drawHDGouraudScanline(DrawingArea.pixels, y2, x1 >> 16, x2 >> 16, r1, g1, b1, r2, g2, b2);
						x1 += dx1;
						x2 += dx2;
						r1 += dr1;
						g1 += dg1;
						b1 += db1;
						r2 += dr2;
						g2 += dg2;
						b2 += db2;
					}
					while(--y1 >= 0) {
						drawHDGouraudScanline(DrawingArea.pixels, y2, x1 >> 16, x3 >> 16, r1, g1, b1, r3, g3, b3);
						x1 += dx1;
						x3 += dx3;
						r1 += dr1;
						g1 += dg1;
						b1 += db1;
						r3 += dr3;
						g3 += dg3;
						b3 += db3;
						y2 += DrawingArea.width;
					}
					return;
				}
				y1 -= y3;
				y3 -= y2;
				for(y2 = lineOffsets[y2]; --y3 >= 0; y2 += DrawingArea.width) {
					drawHDGouraudScanline(DrawingArea.pixels, y2, x2 >> 16, x1 >> 16, r2, g2, b2, r1, g1, b1);
					x1 += dx1;
					x2 += dx2;
					r1 += dr1;
					g1 += dg1;
					b1 += db1;
					r2 += dr2;
					g2 += dg2;
					b2 += db2;
				}
				while(--y1 >= 0) {
					drawHDGouraudScanline(DrawingArea.pixels, y2, x3 >> 16, x1 >> 16, r3, g3, b3, r1, g1, b1);
					x1 += dx1;
					x3 += dx3;
					r1 += dr1;
					g1 += dg1;
					b1 += db1;
					r3 += dr3;
					g3 += dg3;
					b3 += db3;
					y2 += DrawingArea.width;
				}
				return;
			}
			x3 = x2 <<= 16;
			r3 = r2 <<= 16;
			g3 = g2 <<= 16;
			b3 = b2 <<= 16;
			if(y2 < 0) {
				x3 -= dx1 * y2;
				x2 -= dx2 * y2;
				r3 -= dr1 * y2;
				g3 -= dg1 * y2;
				b3 -= db1 * y2;
				r2 -= dr2 * y2;
				g2 -= dg2 * y2;
				b2 -= db2 * y2;
				y2 = 0;
			}
			x1 <<= 16;
			r1 <<= 16;
			g1 <<= 16;
			b1 <<= 16;
			if(y1 < 0) {
				x1 -= dx3 * y1;
				r1 -= dr3 * y1;
				g1 -= dg3 * y1;
				b1 -= db3 * y1;
				y1 = 0;
			}
			if(dx1 < dx2) {
				y3 -= y1;
				y1 -= y2;
				for(y2 = lineOffsets[y2]; --y1 >= 0; y2 += DrawingArea.width) {
					drawHDGouraudScanline(DrawingArea.pixels, y2, x3 >> 16, x2 >> 16, r3, g3, b3, r2, g2, b2);
					x3 += dx1;
					x2 += dx2;
					r3 += dr1;
					g3 += dg1;
					b3 += db1;
					r2 += dr2;
					g2 += dg2;
					b2 += db2;
				}
				while(--y3 >= 0) {
					drawHDGouraudScanline(DrawingArea.pixels, y2, x1 >> 16, x2 >> 16, r1, g1, b1, r2, g2, b2);
					x1 += dx3;
					x2 += dx2;
					r1 += dr3;
					g1 += dg3;
					b1 += db3;
					r2 += dr2;
					g2 += dg2;
					b2 += db2;
					y2 += DrawingArea.width;
				}
				return;
			}
			y3 -= y1;
			y1 -= y2;
			for(y2 = lineOffsets[y2]; --y1 >= 0; y2 += DrawingArea.width) {
				drawHDGouraudScanline(DrawingArea.pixels, y2, x2 >> 16, x3 >> 16, r2, g2, b2, r3, g3, b3);
				x3 += dx1;
				x2 += dx2;
				r3 += dr1;
				g3 += dg1;
				b3 += db1;
				r2 += dr2;
				g2 += dg2;
				b2 += db2;
			}
			while(--y3 >= 0) {
				drawHDGouraudScanline(DrawingArea.pixels, y2, x2 >> 16, x1 >> 16, r2, g2, b2, r1, g1, b1);
				x1 += dx3;
				x2 += dx2;
				r1 += dr3;
				g1 += dg3;
				b1 += db3;
				r2 += dr2;
				g2 += dg2;
				b2 += db2;
				y2 += DrawingArea.width;
			}
			return;
		}
		if(y3 >= DrawingArea.bottomY) {
			return;
		}
		if(y1 > DrawingArea.bottomY) {
			y1 = DrawingArea.bottomY;
		}
		if(y2 > DrawingArea.bottomY) {
			y2 = DrawingArea.bottomY;
		}
		if(y1 < y2) {
			x2 = x3 <<= 16;
			r2 = r3 <<= 16;
			g2 = g3 <<= 16;
			b2 = b3 <<= 16;
			if(y3 < 0) {
				x2 -= dx2 * y3;
				x3 -= dx3 * y3;
				r2 -= dr2 * y3;
				g2 -= dg2 * y3;
				b2 -= db2 * y3;
				r3 -= dr3 * y3;
				g3 -= dg3 * y3;
				b3 -= db3 * y3;
				y3 = 0;
			}
			x1 <<= 16;
			r1 <<= 16;
			g1 <<= 16;
			b1 <<= 16;
			if(y1 < 0) {
				x1 -= dx1 * y1;
				r1 -= dr1 * y1;
				g1 -= dg1 * y1;
				b1 -= db1 * y1;
				y1 = 0;
			}
			if(dx2 < dx3) {
				y2 -= y1;
				y1 -= y3;
				for(y3 = lineOffsets[y3]; --y1 >= 0; y3 += DrawingArea.width) {
					drawHDGouraudScanline(DrawingArea.pixels, y3, x2 >> 16, x3 >> 16, r2, g2, b2, r3, g3, b3);
					x2 += dx2;
					x3 += dx3;
					r2 += dr2;
					g2 += dg2;
					b2 += db2;
					r3 += dr3;
					g3 += dg3;
					b3 += db3;
				}
				while(--y2 >= 0) {
					drawHDGouraudScanline(DrawingArea.pixels, y3, x2 >> 16, x1 >> 16, r2, g2, b2, r1, g1, b1);
					x2 += dx2;
					x1 += dx1;
					r2 += dr2;
					g2 += dg2;
					b2 += db2;
					r1 += dr1;
					g1 += dg1;
					b1 += db1;
					y3 += DrawingArea.width;
				}
				return;
			}
			y2 -= y1;
			y1 -= y3;
			for(y3 = lineOffsets[y3]; --y1 >= 0; y3 += DrawingArea.width) {
				drawHDGouraudScanline(DrawingArea.pixels, y3, x3 >> 16, x2 >> 16, r3, g3, b3, r2, g2, b2);
				x2 += dx2;
				x3 += dx3;
				r2 += dr2;
				g2 += dg2;
				b2 += db2;
				r3 += dr3;
				g3 += dg3;
				b3 += db3;
			}
			while(--y2 >= 0) {
				drawHDGouraudScanline(DrawingArea.pixels, y3, x1 >> 16, x2 >> 16, r1, g1, b1, r2, g2, b2);
				x2 += dx2;
				x1 += dx1;
				r2 += dr2;
				g2 += dg2;
				b2 += db2;
				r1 += dr1;
				g1 += dg1;
				b1 += db1;
				y3 += DrawingArea.width;
			}
			return;
		}
		x1 = x3 <<= 16;
		r1 = r3 <<= 16;
		g1 = g3 <<= 16;
		b1 = b3 <<= 16;
		if(y3 < 0) {
			x1 -= dx2 * y3;
			x3 -= dx3 * y3;
			r1 -= dr2 * y3;
			g1 -= dg2 * y3;
			b1 -= db2 * y3;
			r3 -= dr3 * y3;
			g3 -= dg3 * y3;
			b3 -= db3 * y3;
			y3 = 0;
		}
		x2 <<= 16;
		r2 <<= 16;
		g2 <<= 16;
		b2 <<= 16;
		if(y2 < 0) {
			x2 -= dx1 * y2;
			r2 -= dr1 * y2;
			g2 -= dg1 * y2;
			b2 -= db1 * y2;
			y2 = 0;
		}
		if(dx2 < dx3) {
			y1 -= y2;
			y2 -= y3;
			for(y3 = lineOffsets[y3]; --y2 >= 0; y3 += DrawingArea.width) {
				drawHDGouraudScanline(DrawingArea.pixels, y3, x1 >> 16, x3 >> 16, r1, g1, b1, r3, g3, b3);
				x1 += dx2;
				x3 += dx3;
				r1 += dr2;
				g1 += dg2;
				b1 += db2;
				r3 += dr3;
				g3 += dg3;
				b3 += db3;
			}
			while(--y1 >= 0) {
				drawHDGouraudScanline(DrawingArea.pixels, y3, x2 >> 16, x3 >> 16, r2, g2, b2, r3, g3, b3);
				x2 += dx1;
				x3 += dx3;
				r2 += dr1;
				g2 += dg1;
				b2 += db1;
				r3 += dr3;
				g3 += dg3;
				b3 += db3;
				y3 += DrawingArea.width;
			}
			return;
		}
		y1 -= y2;
		y2 -= y3;
		for(y3 = lineOffsets[y3]; --y2 >= 0; y3 += DrawingArea.width) {
			drawHDGouraudScanline(DrawingArea.pixels, y3, x3 >> 16, x1 >> 16, r3, g3, b3, r1, g1, b1);
			x1 += dx2;
			x3 += dx3;
			r1 += dr2;
			g1 += dg2;
			b1 += db2;
			r3 += dr3;
			g3 += dg3;
			b3 += db3;
		}
		while(--y1 >= 0) {
			drawHDGouraudScanline(DrawingArea.pixels, y3, x3 >> 16, x2 >> 16, r3, g3, b3, r2, g2, b2);
			x2 += dx1;
			x3 += dx3;
			r2 += dr1;
			g2 += dg1;
			b2 += db1;
			r3 += dr3;
			g3 += dg3;
			b3 += db3;
			y3 += DrawingArea.width;
		}
	}

	
	public static void drawHDGouraudScanline(int[] dest, int offset, int x1, int x2, int r1, int g1, int b1, int r2, int g2, int b2) {
		int n = x2 - x1;
		if (n <= 0) {
			return;
		}
		r2 = (r2 - r1) / n;
		g2 = (g2 - g1) / n;
		b2 = (b2 - b1) / n;
		if (aBoolean1462) {
			if (x2 > DrawingArea.centerX) {
				n -= x2 - DrawingArea.centerX;
				x2 = DrawingArea.centerX;
			}
			if (x1 < 0) {
				n = x2;
				r1 -= x1 * r2;
				g1 -= x1 * g2;
				b1 -= x1 * b2;
				x1 = 0;
			}
		}
		if (x1 < x2) {
			offset += x1;
			if (anInt1465 == 0) {
				while (--n >= 0) {
					dest[offset] = (r1 & 0xff0000) | (g1 >> 8 & 0xff00) | (b1 >> 16 & 0xff);
					r1 += r2;
					g1 += g2;
					b1 += b2;
					offset++;
				}
			} else {
				final int a1 = anInt1465;
				final int a2 = 256 - anInt1465;
				int rgb;
				int dst;
				while (--n >= 0) {
					rgb = (r1 & 0xff0000) | (g1 >> 8 & 0xff00) | (b1 >> 16 & 0xff);
					rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff) + ((rgb & 0xff00) * a2 >> 8 & 0xff00);
					dst = dest[offset];
					dest[offset] = rgb + ((dst & 0xff00ff) * a1 >> 8 & 0xff00ff) + ((dst & 0xff00) * a1 >> 8 & 0xff00);
					r1 += r2;
					g1 += g2;
					b1 += b2;
					offset++;
				}
			}
		}
	}

	public static void method376(int i, int j, int k, int l, int i1, int j1, int k1)
	{
		int l1 = 0;
		if(j != i)
			l1 = (i1 - l << 16) / (j - i);
		int i2 = 0;
		if(k != j)
			i2 = (j1 - i1 << 16) / (k - j);
		int j2 = 0;
		if(k != i)
			j2 = (l - j1 << 16) / (i - k);
		if(i <= j && i <= k)
		{
			if(i >= DrawingArea.bottomY)
				return;
			if(j > DrawingArea.bottomY)
				j = DrawingArea.bottomY;
			if(k > DrawingArea.bottomY)
				k = DrawingArea.bottomY;
			if(j < k)
			{
				j1 = l <<= 16;
				if(i < 0)
				{
					j1 -= j2 * i;
					l -= l1 * i;
					i = 0;
				}
				i1 <<= 16;
				if(j < 0)
				{
					i1 -= i2 * j;
					j = 0;
				}
				if(i != j && j2 < l1 || i == j && j2 > i2)
				{
					k -= j;
					j -= i;
					for(i = lineOffsets[i]; --j >= 0; i += DrawingArea.width)
					{
						method377(DrawingArea.pixels, i, k1, j1 >> 16, l >> 16);
						j1 += j2;
						l += l1;
					}

					while(--k >= 0) 
					{
						method377(DrawingArea.pixels, i, k1, j1 >> 16, i1 >> 16);
						j1 += j2;
						i1 += i2;
						i += DrawingArea.width;
					}
					return;
				}
				k -= j;
				j -= i;
				for(i = lineOffsets[i]; --j >= 0; i += DrawingArea.width)
				{
					method377(DrawingArea.pixels, i, k1, l >> 16, j1 >> 16);
					j1 += j2;
					l += l1;
				}

				while(--k >= 0) 
				{
					method377(DrawingArea.pixels, i, k1, i1 >> 16, j1 >> 16);
					j1 += j2;
					i1 += i2;
					i += DrawingArea.width;
				}
				return;
			}
			i1 = l <<= 16;
			if(i < 0)
			{
				i1 -= j2 * i;
				l -= l1 * i;
				i = 0;
			}
			j1 <<= 16;
			if(k < 0)
			{
				j1 -= i2 * k;
				k = 0;
			}
			if(i != k && j2 < l1 || i == k && i2 > l1)
			{
				j -= k;
				k -= i;
				for(i = lineOffsets[i]; --k >= 0; i += DrawingArea.width)
				{
					method377(DrawingArea.pixels, i, k1, i1 >> 16, l >> 16);
					i1 += j2;
					l += l1;
				}

				while(--j >= 0) 
				{
					method377(DrawingArea.pixels, i, k1, j1 >> 16, l >> 16);
					j1 += i2;
					l += l1;
					i += DrawingArea.width;
				}
				return;
			}
			j -= k;
			k -= i;
			for(i = lineOffsets[i]; --k >= 0; i += DrawingArea.width)
			{
				method377(DrawingArea.pixels, i, k1, l >> 16, i1 >> 16);
				i1 += j2;
				l += l1;
			}

			while(--j >= 0) 
			{
				method377(DrawingArea.pixels, i, k1, l >> 16, j1 >> 16);
				j1 += i2;
				l += l1;
				i += DrawingArea.width;
			}
			return;
		}
		if(j <= k)
		{
			if(j >= DrawingArea.bottomY)
				return;
			if(k > DrawingArea.bottomY)
				k = DrawingArea.bottomY;
			if(i > DrawingArea.bottomY)
				i = DrawingArea.bottomY;
			if(k < i)
			{
				l = i1 <<= 16;
				if(j < 0)
				{
					l -= l1 * j;
					i1 -= i2 * j;
					j = 0;
				}
				j1 <<= 16;
				if(k < 0)
				{
					j1 -= j2 * k;
					k = 0;
				}
				if(j != k && l1 < i2 || j == k && l1 > j2)
				{
					i -= k;
					k -= j;
					for(j = lineOffsets[j]; --k >= 0; j += DrawingArea.width)
					{
						method377(DrawingArea.pixels, j, k1, l >> 16, i1 >> 16);
						l += l1;
						i1 += i2;
					}

					while(--i >= 0) 
					{
						method377(DrawingArea.pixels, j, k1, l >> 16, j1 >> 16);
						l += l1;
						j1 += j2;
						j += DrawingArea.width;
					}
					return;
				}
				i -= k;
				k -= j;
				for(j = lineOffsets[j]; --k >= 0; j += DrawingArea.width)
				{
					method377(DrawingArea.pixels, j, k1, i1 >> 16, l >> 16);
					l += l1;
					i1 += i2;
				}

				while(--i >= 0) 
				{
					method377(DrawingArea.pixels, j, k1, j1 >> 16, l >> 16);
					l += l1;
					j1 += j2;
					j += DrawingArea.width;
				}
				return;
			}
			j1 = i1 <<= 16;
			if(j < 0)
			{
				j1 -= l1 * j;
				i1 -= i2 * j;
				j = 0;
			}
			l <<= 16;
			if(i < 0)
			{
				l -= j2 * i;
				i = 0;
			}
			if(l1 < i2)
			{
				k -= i;
				i -= j;
				for(j = lineOffsets[j]; --i >= 0; j += DrawingArea.width)
				{
					method377(DrawingArea.pixels, j, k1, j1 >> 16, i1 >> 16);
					j1 += l1;
					i1 += i2;
				}

				while(--k >= 0) 
				{
					method377(DrawingArea.pixels, j, k1, l >> 16, i1 >> 16);
					l += j2;
					i1 += i2;
					j += DrawingArea.width;
				}
				return;
			}
			k -= i;
			i -= j;
			for(j = lineOffsets[j]; --i >= 0; j += DrawingArea.width)
			{
				method377(DrawingArea.pixels, j, k1, i1 >> 16, j1 >> 16);
				j1 += l1;
				i1 += i2;
			}

			while(--k >= 0) 
			{
				method377(DrawingArea.pixels, j, k1, i1 >> 16, l >> 16);
				l += j2;
				i1 += i2;
				j += DrawingArea.width;
			}
			return;
		}
		if(k >= DrawingArea.bottomY)
			return;
		if(i > DrawingArea.bottomY)
			i = DrawingArea.bottomY;
		if(j > DrawingArea.bottomY)
			j = DrawingArea.bottomY;
		if(i < j)
		{
			i1 = j1 <<= 16;
			if(k < 0)
			{
				i1 -= i2 * k;
				j1 -= j2 * k;
				k = 0;
			}
			l <<= 16;
			if(i < 0)
			{
				l -= l1 * i;
				i = 0;
			}
			if(i2 < j2)
			{
				j -= i;
				i -= k;
				for(k = lineOffsets[k]; --i >= 0; k += DrawingArea.width)
				{
					method377(DrawingArea.pixels, k, k1, i1 >> 16, j1 >> 16);
					i1 += i2;
					j1 += j2;
				}

				while(--j >= 0) 
				{
					method377(DrawingArea.pixels, k, k1, i1 >> 16, l >> 16);
					i1 += i2;
					l += l1;
					k += DrawingArea.width;
				}
				return;
			}
			j -= i;
			i -= k;
			for(k = lineOffsets[k]; --i >= 0; k += DrawingArea.width)
			{
				method377(DrawingArea.pixels, k, k1, j1 >> 16, i1 >> 16);
				i1 += i2;
				j1 += j2;
			}

			while(--j >= 0) 
			{
				method377(DrawingArea.pixels, k, k1, l >> 16, i1 >> 16);
				i1 += i2;
				l += l1;
				k += DrawingArea.width;
			}
			return;
		}
		l = j1 <<= 16;
		if(k < 0)
		{
			l -= i2 * k;
			j1 -= j2 * k;
			k = 0;
		}
		i1 <<= 16;
		if(j < 0)
		{
			i1 -= l1 * j;
			j = 0;
		}
		if(i2 < j2)
		{
			i -= j;
			j -= k;
			for(k = lineOffsets[k]; --j >= 0; k += DrawingArea.width)
			{
				method377(DrawingArea.pixels, k, k1, l >> 16, j1 >> 16);
				l += i2;
				j1 += j2;
			}

			while(--i >= 0) 
			{
				method377(DrawingArea.pixels, k, k1, i1 >> 16, j1 >> 16);
				i1 += l1;
				j1 += j2;
				k += DrawingArea.width;
			}
			return;
		}
		i -= j;
		j -= k;
		for(k = lineOffsets[k]; --j >= 0; k += DrawingArea.width)
		{
			method377(DrawingArea.pixels, k, k1, j1 >> 16, l >> 16);
			l += i2;
			j1 += j2;
		}

		while(--i >= 0) 
		{
			method377(DrawingArea.pixels, k, k1, j1 >> 16, i1 >> 16);
			i1 += l1;
			j1 += j2;
			k += DrawingArea.width;
		}
	}

	private static void method377(int ai[], int i, int j, int l, int i1)
	{
		int k;//was parameter
		if(aBoolean1462)
		{
			if(i1 > DrawingArea.centerX)
				i1 = DrawingArea.centerX;
			if(l < 0)
				l = 0;
		}
		if(l >= i1)
			return;
		i += l;
		k = i1 - l >> 2;
		if(anInt1465 == 0)
		{
			while(--k >= 0) 
			{
				ai[i++] = j;
				ai[i++] = j;
				ai[i++] = j;
				ai[i++] = j;
			}
			for(k = i1 - l & 3; --k >= 0;)
				ai[i++] = j;

			return;
		}
		int j1 = anInt1465;
		int k1 = 256 - anInt1465;
		j = ((j & 0xff00ff) * k1 >> 8 & 0xff00ff) + ((j & 0xff00) * k1 >> 8 & 0xff00);
		while(--k >= 0) 
		{
			ai[i++] = j + ((ai[i] & 0xff00ff) * j1 >> 8 & 0xff00ff) + ((ai[i] & 0xff00) * j1 >> 8 & 0xff00);
			ai[i++] = j + ((ai[i] & 0xff00ff) * j1 >> 8 & 0xff00ff) + ((ai[i] & 0xff00) * j1 >> 8 & 0xff00);
			ai[i++] = j + ((ai[i] & 0xff00ff) * j1 >> 8 & 0xff00ff) + ((ai[i] & 0xff00) * j1 >> 8 & 0xff00);
			ai[i++] = j + ((ai[i] & 0xff00ff) * j1 >> 8 & 0xff00ff) + ((ai[i] & 0xff00) * j1 >> 8 & 0xff00);
		}
		for(k = i1 - l & 3; --k >= 0;)
			ai[i++] = j + ((ai[i] & 0xff00ff) * j1 >> 8 & 0xff00ff) + ((ai[i] & 0xff00) * j1 >> 8 & 0xff00);

	}
	
	public static void method378(int i, int j, int k, int l, int i1, int j1, int k1, int l1, 
			int i2, int j2, int k2, int l2, int i3, int j3, int k3, 
			int l3, int i4, int j4, int k4) {
		if (Configuration.smoothShading && aBoolean1464) {
			drawHDTexturedTriangle(i, j, k, l, i1, j1, k1, l1, i2, j2, k2, l2, i3, j3, k3, l3, i4, j4, k4);
		} else {
			drawLDTexturedTriangle(i, j, k, l, i1, j1, k1, l1, i2, j2, k2, l2, i3, j3, k3, l3, i4, j4, k4);
		}
	}

	public static void drawLDTexturedTriangle(int i, int j, int k, int l, int i1, int j1, int k1, int l1, 
			int i2, int j2, int k2, int l2, int i3, int j3, int k3, 
			int l3, int i4, int j4, int k4)
	{
		int ai[] = method371(k4);
		aBoolean1463 = !aBooleanArray1475[k4];
		k2 = j2 - k2;
		j3 = i3 - j3;
		i4 = l3 - i4;
		l2 -= j2;
		k3 -= i3;
		j4 -= l3;
		int l4 = l2 * i3 - k3 * j2 << (WorldController.viewDistance == 9 ? 14 : 15);
		int i5 = k3 * l3 - j4 * i3 << 8;
		int j5 = j4 * j2 - l2 * l3 << 5;
		int k5 = k2 * i3 - j3 * j2 << (WorldController.viewDistance == 9 ? 14 : 15);
		int l5 = j3 * l3 - i4 * i3 << 8;
		int i6 = i4 * j2 - k2 * l3 << 5;
		int j6 = j3 * l2 - k2 * k3 << (WorldController.viewDistance == 9 ? 14 : 15);
		int k6 = i4 * k3 - j3 * j4 << 8;
		int l6 = k2 * j4 - i4 * l2 << 5;
		int i7 = 0;
		int j7 = 0;
		if(j != i)
		{
			i7 = (i1 - l << 16) / (j - i);
			j7 = (l1 - k1 << 16) / (j - i);
		}
		int k7 = 0;
		int l7 = 0;
		if(k != j)
		{
			k7 = (j1 - i1 << 16) / (k - j);
			l7 = (i2 - l1 << 16) / (k - j);
		}
		int i8 = 0;
		int j8 = 0;
		if(k != i)
		{
			i8 = (l - j1 << 16) / (i - k);
			j8 = (k1 - i2 << 16) / (i - k);
		}
		if(i <= j && i <= k)
		{
			if(i >= DrawingArea.bottomY)
				return;
			if(j > DrawingArea.bottomY)
				j = DrawingArea.bottomY;
			if(k > DrawingArea.bottomY)
				k = DrawingArea.bottomY;
			if(j < k)
			{
				j1 = l <<= 16;
				i2 = k1 <<= 16;
				if(i < 0)
				{
					j1 -= i8 * i;
					l -= i7 * i;
					i2 -= j8 * i;
					k1 -= j7 * i;
					i = 0;
				}
				i1 <<= 16;
				l1 <<= 16;
				if(j < 0)
				{
					i1 -= k7 * j;
					l1 -= l7 * j;
					j = 0;
				}
				int k8 = i - centerY;
				l4 += j5 * k8;
				k5 += i6 * k8;
				j6 += l6 * k8;
				if(i != j && i8 < i7 || i == j && i8 > k7)
				{
					k -= j;
					j -= i;
					i = lineOffsets[i];
					while(--j >= 0) 
					{
						drawLDTexturedScanline(DrawingArea.pixels, ai, i, j1 >> 16, l >> 16, i2 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
						j1 += i8;
						l += i7;
						i2 += j8;
						k1 += j7;
						i += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while(--k >= 0) 
					{
						drawLDTexturedScanline(DrawingArea.pixels, ai, i, j1 >> 16, i1 >> 16, i2 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
						j1 += i8;
						i1 += k7;
						i2 += j8;
						l1 += l7;
						i += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				k -= j;
				j -= i;
				i = lineOffsets[i];
				while(--j >= 0) 
				{
					drawLDTexturedScanline(DrawingArea.pixels, ai, i, l >> 16, j1 >> 16, k1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
					j1 += i8;
					l += i7;
					i2 += j8;
					k1 += j7;
					i += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while(--k >= 0) 
				{
					drawLDTexturedScanline(DrawingArea.pixels, ai, i, i1 >> 16, j1 >> 16, l1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
					j1 += i8;
					i1 += k7;
					i2 += j8;
					l1 += l7;
					i += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			i1 = l <<= 16;
			l1 = k1 <<= 16;
			if(i < 0)
			{
				i1 -= i8 * i;
				l -= i7 * i;
				l1 -= j8 * i;
				k1 -= j7 * i;
				i = 0;
			}
			j1 <<= 16;
			i2 <<= 16;
			if(k < 0)
			{
				j1 -= k7 * k;
				i2 -= l7 * k;
				k = 0;
			}
			int l8 = i - centerY;
			l4 += j5 * l8;
			k5 += i6 * l8;
			j6 += l6 * l8;
			if(i != k && i8 < i7 || i == k && k7 > i7)
			{
				j -= k;
				k -= i;
				i = lineOffsets[i];
				while(--k >= 0) 
				{
					drawLDTexturedScanline(DrawingArea.pixels, ai, i, i1 >> 16, l >> 16, l1 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
					i1 += i8;
					l += i7;
					l1 += j8;
					k1 += j7;
					i += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while(--j >= 0) 
				{
					drawLDTexturedScanline(DrawingArea.pixels, ai, i, j1 >> 16, l >> 16, i2 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
					j1 += k7;
					l += i7;
					i2 += l7;
					k1 += j7;
					i += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			j -= k;
			k -= i;
			i = lineOffsets[i];
			while(--k >= 0) 
			{
				drawLDTexturedScanline(DrawingArea.pixels, ai, i, l >> 16, i1 >> 16, k1 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
				i1 += i8;
				l += i7;
				l1 += j8;
				k1 += j7;
				i += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while(--j >= 0) 
			{
				drawLDTexturedScanline(DrawingArea.pixels, ai, i, l >> 16, j1 >> 16, k1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
				j1 += k7;
				l += i7;
				i2 += l7;
				k1 += j7;
				i += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if(j <= k)
		{
			if(j >= DrawingArea.bottomY)
				return;
			if(k > DrawingArea.bottomY)
				k = DrawingArea.bottomY;
			if(i > DrawingArea.bottomY)
				i = DrawingArea.bottomY;
			if(k < i)
			{
				l = i1 <<= 16;
				k1 = l1 <<= 16;
				if(j < 0)
				{
					l -= i7 * j;
					i1 -= k7 * j;
					k1 -= j7 * j;
					l1 -= l7 * j;
					j = 0;
				}
				j1 <<= 16;
				i2 <<= 16;
				if(k < 0)
				{
					j1 -= i8 * k;
					i2 -= j8 * k;
					k = 0;
				}
				int i9 = j - centerY;
				l4 += j5 * i9;
				k5 += i6 * i9;
				j6 += l6 * i9;
				if(j != k && i7 < k7 || j == k && i7 > i8)
				{
					i -= k;
					k -= j;
					j = lineOffsets[j];
					while(--k >= 0) 
					{
						drawLDTexturedScanline(DrawingArea.pixels, ai, j, l >> 16, i1 >> 16, k1 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
						l += i7;
						i1 += k7;
						k1 += j7;
						l1 += l7;
						j += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while(--i >= 0) 
					{
						drawLDTexturedScanline(DrawingArea.pixels, ai, j, l >> 16, j1 >> 16, k1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
						l += i7;
						j1 += i8;
						k1 += j7;
						i2 += j8;
						j += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				i -= k;
				k -= j;
				j = lineOffsets[j];
				while(--k >= 0) 
				{
					drawLDTexturedScanline(DrawingArea.pixels, ai, j, i1 >> 16, l >> 16, l1 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
					l += i7;
					i1 += k7;
					k1 += j7;
					l1 += l7;
					j += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while(--i >= 0) 
				{
					drawLDTexturedScanline(DrawingArea.pixels, ai, j, j1 >> 16, l >> 16, i2 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
					l += i7;
					j1 += i8;
					k1 += j7;
					i2 += j8;
					j += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			j1 = i1 <<= 16;
			i2 = l1 <<= 16;
			if(j < 0)
			{
				j1 -= i7 * j;
				i1 -= k7 * j;
				i2 -= j7 * j;
				l1 -= l7 * j;
				j = 0;
			}
			l <<= 16;
			k1 <<= 16;
			if(i < 0)
			{
				l -= i8 * i;
				k1 -= j8 * i;
				i = 0;
			}
			int j9 = j - centerY;
			l4 += j5 * j9;
			k5 += i6 * j9;
			j6 += l6 * j9;
			if(i7 < k7)
			{
				k -= i;
				i -= j;
				j = lineOffsets[j];
				while(--i >= 0) 
				{
					drawLDTexturedScanline(DrawingArea.pixels, ai, j, j1 >> 16, i1 >> 16, i2 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
					j1 += i7;
					i1 += k7;
					i2 += j7;
					l1 += l7;
					j += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while(--k >= 0) 
				{
					drawLDTexturedScanline(DrawingArea.pixels, ai, j, l >> 16, i1 >> 16, k1 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
					l += i8;
					i1 += k7;
					k1 += j8;
					l1 += l7;
					j += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			k -= i;
			i -= j;
			j = lineOffsets[j];
			while(--i >= 0) 
			{
				drawLDTexturedScanline(DrawingArea.pixels, ai, j, i1 >> 16, j1 >> 16, l1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
				j1 += i7;
				i1 += k7;
				i2 += j7;
				l1 += l7;
				j += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while(--k >= 0) 
			{
				drawLDTexturedScanline(DrawingArea.pixels, ai, j, i1 >> 16, l >> 16, l1 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
				l += i8;
				i1 += k7;
				k1 += j8;
				l1 += l7;
				j += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if(k >= DrawingArea.bottomY)
			return;
		if(i > DrawingArea.bottomY)
			i = DrawingArea.bottomY;
		if(j > DrawingArea.bottomY)
			j = DrawingArea.bottomY;
		if(i < j)
		{
			i1 = j1 <<= 16;
			l1 = i2 <<= 16;
			if(k < 0)
			{
				i1 -= k7 * k;
				j1 -= i8 * k;
				l1 -= l7 * k;
				i2 -= j8 * k;
				k = 0;
			}
			l <<= 16;
			k1 <<= 16;
			if(i < 0)
			{
				l -= i7 * i;
				k1 -= j7 * i;
				i = 0;
			}
			int k9 = k - centerY;
			l4 += j5 * k9;
			k5 += i6 * k9;
			j6 += l6 * k9;
			if(k7 < i8)
			{
				j -= i;
				i -= k;
				k = lineOffsets[k];
				while(--i >= 0) 
				{
					drawLDTexturedScanline(DrawingArea.pixels, ai, k, i1 >> 16, j1 >> 16, l1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
					i1 += k7;
					j1 += i8;
					l1 += l7;
					i2 += j8;
					k += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while(--j >= 0) 
				{
					drawLDTexturedScanline(DrawingArea.pixels, ai, k, i1 >> 16, l >> 16, l1 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
					i1 += k7;
					l += i7;
					l1 += l7;
					k1 += j7;
					k += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			j -= i;
			i -= k;
			k = lineOffsets[k];
			while(--i >= 0) 
			{
				drawLDTexturedScanline(DrawingArea.pixels, ai, k, j1 >> 16, i1 >> 16, i2 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
				i1 += k7;
				j1 += i8;
				l1 += l7;
				i2 += j8;
				k += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while(--j >= 0) 
			{
				drawLDTexturedScanline(DrawingArea.pixels, ai, k, l >> 16, i1 >> 16, k1 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
				i1 += k7;
				l += i7;
				l1 += l7;
				k1 += j7;
				k += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		l = j1 <<= 16;
		k1 = i2 <<= 16;
		if(k < 0)
		{
			l -= k7 * k;
			j1 -= i8 * k;
			k1 -= l7 * k;
			i2 -= j8 * k;
			k = 0;
		}
		i1 <<= 16;
		l1 <<= 16;
		if(j < 0)
		{
			i1 -= i7 * j;
			l1 -= j7 * j;
			j = 0;
		}
		int l9 = k - centerY;
		l4 += j5 * l9;
		k5 += i6 * l9;
		j6 += l6 * l9;
		if(k7 < i8)
		{
			i -= j;
			j -= k;
			k = lineOffsets[k];
			while(--j >= 0) 
			{
				drawLDTexturedScanline(DrawingArea.pixels, ai, k, l >> 16, j1 >> 16, k1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
				l += k7;
				j1 += i8;
				k1 += l7;
				i2 += j8;
				k += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while(--i >= 0) 
			{
				drawLDTexturedScanline(DrawingArea.pixels, ai, k, i1 >> 16, j1 >> 16, l1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
				i1 += i7;
				j1 += i8;
				l1 += j7;
				i2 += j8;
				k += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		i -= j;
		j -= k;
		k = lineOffsets[k];
		while(--j >= 0) 
		{
			drawLDTexturedScanline(DrawingArea.pixels, ai, k, j1 >> 16, l >> 16, i2 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
			l += k7;
			j1 += i8;
			k1 += l7;
			i2 += j8;
			k += DrawingArea.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
		while(--i >= 0) 
		{
			drawLDTexturedScanline(DrawingArea.pixels, ai, k, j1 >> 16, i1 >> 16, i2 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
			i1 += i7;
			j1 += i8;
			l1 += j7;
			i2 += j8;
			k += DrawingArea.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
	}

	private static void drawLDTexturedScanline(int ai[], int ai1[], int k, int l, int i1, int j1,
								  int k1, int l1, int i2, int j2, int k2, int l2, int i3)
	{
		int i = 0;//was parameter
		int j = 0;//was parameter
		if(l >= i1)
			return;
		int j3;
		int k3;
		if(aBoolean1462)
		{
			j3 = (k1 - j1) / (i1 - l);
			if(i1 > DrawingArea.centerX)
				i1 = DrawingArea.centerX;
			if(l < 0)
			{
				j1 -= l * j3;
				l = 0;
			}
			if(l >= i1)
				return;
			k3 = i1 - l >> 3;
			j3 <<= 12;
			j1 <<= 9;
		} else
		{
			if(i1 - l > 7)
			{
				k3 = i1 - l >> 3;
				j3 = (k1 - j1) * anIntArray1468[k3] >> 6;
			} else
			{
				k3 = 0;
				j3 = 0;
			}
			j1 <<= 9;
		}
		k += l;
		if(lowMem)
		{
			int i4 = 0;
			int k4 = 0;
			int k6 = l - centerX;
			l1 += (k2 >> 3) * k6;
			i2 += (l2 >> 3) * k6;
			j2 += (i3 >> 3) * k6;
			int i5 = j2 >> 12;
			if(i5 != 0)
			{
				i = l1 / i5;
				j = i2 / i5;
				if(i < 0)
					i = 0;
				else
				if(i > 4032)
					i = 4032;
			}
			l1 += k2;
			i2 += l2;
			j2 += i3;
			i5 = j2 >> 12;
			if(i5 != 0)
			{
				i4 = l1 / i5;
				k4 = i2 / i5;
				if(i4 < 7)
					i4 = 7;
				else
				if(i4 > 4032)
					i4 = 4032;
			}
			int i7 = i4 - i >> 3;
			int k7 = k4 - j >> 3;
			i += (j1 & 0x600000) >> 3;
			int i8 = j1 >> 23;
			if(aBoolean1463)
			{
				while(k3-- > 0) 
				{
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i = i4;
					j = k4;
					l1 += k2;
					i2 += l2;
					j2 += i3;
					int j5 = j2 >> 12;
					if(j5 != 0)
					{
						i4 = l1 / j5;
						k4 = i2 / j5;
						if(i4 < 7)
							i4 = 7;
						else
						if(i4 > 4032)
							i4 = 4032;
					}
					i7 = i4 - i >> 3;
					k7 = k4 - j >> 3;
					j1 += j3;
					i += (j1 & 0x600000) >> 3;
					i8 = j1 >> 23;
				}
				for(k3 = i1 - l & 7; k3-- > 0;)
				{
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
				}

				return;
			}
			while(k3-- > 0) 
			{
				int k8;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = k8;
				k++;
				i += i7;
				j += k7;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = k8;
				k++;
				i += i7;
				j += k7;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = k8;
				k++;
				i += i7;
				j += k7;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = k8;
				k++;
				i += i7;
				j += k7;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = k8;
				k++;
				i += i7;
				j += k7;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = k8;
				k++;
				i += i7;
				j += k7;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = k8;
				k++;
				i += i7;
				j += k7;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = k8;
				k++;
				i = i4;
				j = k4;
				l1 += k2;
				i2 += l2;
				j2 += i3;
				int k5 = j2 >> 12;
				if(k5 != 0)
				{
					i4 = l1 / k5;
					k4 = i2 / k5;
					if(i4 < 7)
						i4 = 7;
					else
					if(i4 > 4032)
						i4 = 4032;
				}
				i7 = i4 - i >> 3;
				k7 = k4 - j >> 3;
				j1 += j3;
				i += (j1 & 0x600000) >> 3;
				i8 = j1 >> 23;
			}
			for(k3 = i1 - l & 7; k3-- > 0;)
			{
				int l8;
				if((l8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = l8;
				k++;
				i += i7;
				j += k7;
			}

			return;
		}
		int j4 = 0;
		int l4 = 0;
		int l6 = l - centerX;
		l1 += (k2 >> 3) * l6;
		i2 += (l2 >> 3) * l6;
		j2 += (i3 >> 3) * l6;
		int l5 = j2 >> 14;
		if(l5 != 0)
		{
			i = l1 / l5;
			j = i2 / l5;
			if(i < 0)
				i = 0;
			else
			if(i > 16256)
				i = 16256;
		}
		l1 += k2;
		i2 += l2;
		j2 += i3;
		l5 = j2 >> 14;
		if(l5 != 0)
		{
			j4 = l1 / l5;
			l4 = i2 / l5;
			if(j4 < 7)
				j4 = 7;
			else
			if(j4 > 16256)
				j4 = 16256;
		}
		int j7 = j4 - i >> 3;
		int l7 = l4 - j >> 3;
		i += j1 & 0x600000;
		int j8 = j1 >> 23;
		if(aBoolean1463)
		{
			while(k3-- > 0) 
			{
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i = j4;
				j = l4;
				l1 += k2;
				i2 += l2;
				j2 += i3;
				int i6 = j2 >> 14;
				if(i6 != 0)
				{
					j4 = l1 / i6;
					l4 = i2 / i6;
					if(j4 < 7)
						j4 = 7;
					else
					if(j4 > 16256)
						j4 = 16256;
				}
				j7 = j4 - i >> 3;
				l7 = l4 - j >> 3;
				j1 += j3;
				i += j1 & 0x600000;
				j8 = j1 >> 23;
			}
			for(k3 = i1 - l & 7; k3-- > 0;)
			{
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
			}

			return;
		}
		while(k3-- > 0) 
		{
			int i9;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = i9;
			k++;
			i += j7;
			j += l7;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = i9;
			k++;
			i += j7;
			j += l7;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = i9;
			k++;
			i += j7;
			j += l7;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = i9;
			k++;
			i += j7;
			j += l7;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = i9;
			k++;
			i += j7;
			j += l7;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = i9;
			k++;
			i += j7;
			j += l7;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = i9;
			k++;
			i += j7;
			j += l7;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = i9;
			k++;
			i = j4;
			j = l4;
			l1 += k2;
			i2 += l2;
			j2 += i3;
			int j6 = j2 >> 14;
			if(j6 != 0)
			{
				j4 = l1 / j6;
				l4 = i2 / j6;
				if(j4 < 7)
					j4 = 7;
				else
				if(j4 > 16256)
					j4 = 16256;
			}
			j7 = j4 - i >> 3;
			l7 = l4 - j >> 3;
			j1 += j3;
			i += j1 & 0x600000;
			j8 = j1 >> 23;
		}
		for(int l3 = i1 - l & 7; l3-- > 0;)
		{
			int j9;
			if((j9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = j9;
			k++;
			i += j7;
			j += l7;
		}

	}
	
	public static void drawHDTexturedTriangle(int y1, int y2, int y3, int x1, int x2, int x3, int l1, int l2, 
			int l3, int tx1, int tx2, int tx3, int ty1, int ty2, int ty3, 
			int tz1, int tz2, int tz3, int tex)
	{
		l1 = 0x7f - l1 << 1;
		l2 = 0x7f - l2 << 1;
		l3 = 0x7f - l3 << 1;
		int ai[] = method371(tex);
		aBoolean1463 = !aBooleanArray1475[tex];
		tx2 = tx1 - tx2;
		ty2 = ty1 - ty2;
		tz2 = tz1 - tz2;
		tx3 -= tx1;
		ty3 -= ty1;
		tz3 -= tz1;
		int l4 = tx3 * ty1 - ty3 * tx1 << (WorldController.viewDistance == 9 ? 14 : 15);
		int i5 = ty3 * tz1 - tz3 * ty1 << 8;
		int j5 = tz3 * tx1 - tx3 * tz1 << 5;
		int k5 = tx2 * ty1 - ty2 * tx1 << (WorldController.viewDistance == 9 ? 14 : 15);
		int l5 = ty2 * tz1 - tz2 * ty1 << 8;
		int i6 = tz2 * tx1 - tx2 * tz1 << 5;
		int j6 = ty2 * tx3 - tx2 * ty3 << (WorldController.viewDistance == 9 ? 14 : 15);
		int k6 = tz2 * ty3 - ty2 * tz3 << 8;
		int l6 = tx2 * tz3 - tz2 * tx3 << 5;
		int i7 = 0;
		int j7 = 0;
		if(y2 != y1)
		{
			i7 = (x2 - x1 << 16) / (y2 - y1);
			j7 = (l2 - l1 << 16) / (y2 - y1);
		}
		int k7 = 0;
		int l7 = 0;
		if(y3 != y2)
		{
			k7 = (x3 - x2 << 16) / (y3 - y2);
			l7 = (l3 - l2 << 16) / (y3 - y2);
		}
		int i8 = 0;
		int j8 = 0;
		if(y3 != y1)
		{
			i8 = (x1 - x3 << 16) / (y1 - y3);
			j8 = (l1 - l3 << 16) / (y1 - y3);
		}
		if(y1 <= y2 && y1 <= y3)
		{
			if(y1 >= DrawingArea.bottomY)
				return;
			if(y2 > DrawingArea.bottomY)
				y2 = DrawingArea.bottomY;
			if(y3 > DrawingArea.bottomY)
				y3 = DrawingArea.bottomY;
			if(y2 < y3)
			{
				x3 = x1 <<= 16;
				l3 = l1 <<= 16;
				if(y1 < 0)
				{
					x3 -= i8 * y1;
					x1 -= i7 * y1;
					l3 -= j8 * y1;
					l1 -= j7 * y1;
					y1 = 0;
				}
				x2 <<= 16;
				l2 <<= 16;
				if(y2 < 0)
				{
					x2 -= k7 * y2;
					l2 -= l7 * y2;
					y2 = 0;
				}
				int k8 = y1 - centerY;
				l4 += j5 * k8;
				k5 += i6 * k8;
				j6 += l6 * k8;
				if(y1 != y2 && i8 < i7 || y1 == y2 && i8 > k7)
				{
					y3 -= y2;
					y2 -= y1;
					y1 = lineOffsets[y1];
					while(--y2 >= 0) 
					{
						drawHDTexturedScanline(DrawingArea.pixels, ai, y1, x3 >> 16, x1 >> 16, l3, l1, l4, k5, j6, i5, l5, k6);
						x3 += i8;
						x1 += i7;
						l3 += j8;
						l1 += j7;
						y1 += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while(--y3 >= 0) 
					{
						drawHDTexturedScanline(DrawingArea.pixels, ai, y1, x3 >> 16, x2 >> 16, l3, l2, l4, k5, j6, i5, l5, k6);
						x3 += i8;
						x2 += k7;
						l3 += j8;
						l2 += l7;
						y1 += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				y3 -= y2;
				y2 -= y1;
				y1 = lineOffsets[y1];
				while(--y2 >= 0) 
				{
					drawHDTexturedScanline(DrawingArea.pixels, ai, y1, x1 >> 16, x3 >> 16, l1, l3, l4, k5, j6, i5, l5, k6);
					x3 += i8;
					x1 += i7;
					l3 += j8;
					l1 += j7;
					y1 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while(--y3 >= 0) 
				{
					drawHDTexturedScanline(DrawingArea.pixels, ai, y1, x2 >> 16, x3 >> 16, l2, l3, l4, k5, j6, i5, l5, k6);
					x3 += i8;
					x2 += k7;
					l3 += j8;
					l2 += l7;
					y1 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			x2 = x1 <<= 16;
			l2 = l1 <<= 16;
			if(y1 < 0)
			{
				x2 -= i8 * y1;
				x1 -= i7 * y1;
				l2 -= j8 * y1;
				l1 -= j7 * y1;
				y1 = 0;
			}
			x3 <<= 16;
			l3 <<= 16;
			if(y3 < 0)
			{
				x3 -= k7 * y3;
				l3 -= l7 * y3;
				y3 = 0;
			}
			int l8 = y1 - centerY;
			l4 += j5 * l8;
			k5 += i6 * l8;
			j6 += l6 * l8;
			if(y1 != y3 && i8 < i7 || y1 == y3 && k7 > i7)
			{
				y2 -= y3;
				y3 -= y1;
				y1 = lineOffsets[y1];
				while(--y3 >= 0) 
				{
					drawHDTexturedScanline(DrawingArea.pixels, ai, y1, x2 >> 16, x1 >> 16, l2, l1, l4, k5, j6, i5, l5, k6);
					x2 += i8;
					x1 += i7;
					l2 += j8;
					l1 += j7;
					y1 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while(--y2 >= 0) 
				{
					drawHDTexturedScanline(DrawingArea.pixels, ai, y1, x3 >> 16, x1 >> 16, l3, l1, l4, k5, j6, i5, l5, k6);
					x3 += k7;
					x1 += i7;
					l3 += l7;
					l1 += j7;
					y1 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y2 -= y3;
			y3 -= y1;
			y1 = lineOffsets[y1];
			while(--y3 >= 0) 
			{
				drawHDTexturedScanline(DrawingArea.pixels, ai, y1, x1 >> 16, x2 >> 16, l1, l2, l4, k5, j6, i5, l5, k6);
				x2 += i8;
				x1 += i7;
				l2 += j8;
				l1 += j7;
				y1 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while(--y2 >= 0) 
			{
				drawHDTexturedScanline(DrawingArea.pixels, ai, y1, x1 >> 16, x3 >> 16, l1, l3, l4, k5, j6, i5, l5, k6);
				x3 += k7;
				x1 += i7;
				l3 += l7;
				l1 += j7;
				y1 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if(y2 <= y3)
		{
			if(y2 >= DrawingArea.bottomY)
				return;
			if(y3 > DrawingArea.bottomY)
				y3 = DrawingArea.bottomY;
			if(y1 > DrawingArea.bottomY)
				y1 = DrawingArea.bottomY;
			if(y3 < y1)
			{
				x1 = x2 <<= 16;
				l1 = l2 <<= 16;
				if(y2 < 0)
				{
					x1 -= i7 * y2;
					x2 -= k7 * y2;
					l1 -= j7 * y2;
					l2 -= l7 * y2;
					y2 = 0;
				}
				x3 <<= 16;
				l3 <<= 16;
				if(y3 < 0)
				{
					x3 -= i8 * y3;
					l3 -= j8 * y3;
					y3 = 0;
				}
				int i9 = y2 - centerY;
				l4 += j5 * i9;
				k5 += i6 * i9;
				j6 += l6 * i9;
				if(y2 != y3 && i7 < k7 || y2 == y3 && i7 > i8)
				{
					y1 -= y3;
					y3 -= y2;
					y2 = lineOffsets[y2];
					while(--y3 >= 0) 
					{
						drawHDTexturedScanline(DrawingArea.pixels, ai, y2, x1 >> 16, x2 >> 16, l1, l2, l4, k5, j6, i5, l5, k6);
						x1 += i7;
						x2 += k7;
						l1 += j7;
						l2 += l7;
						y2 += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while(--y1 >= 0) 
					{
						drawHDTexturedScanline(DrawingArea.pixels, ai, y2, x1 >> 16, x3 >> 16, l1, l3, l4, k5, j6, i5, l5, k6);
						x1 += i7;
						x3 += i8;
						l1 += j7;
						l3 += j8;
						y2 += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				y1 -= y3;
				y3 -= y2;
				y2 = lineOffsets[y2];
				while(--y3 >= 0) 
				{
					drawHDTexturedScanline(DrawingArea.pixels, ai, y2, x2 >> 16, x1 >> 16, l2, l1, l4, k5, j6, i5, l5, k6);
					x1 += i7;
					x2 += k7;
					l1 += j7;
					l2 += l7;
					y2 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while(--y1 >= 0) 
				{
					drawHDTexturedScanline(DrawingArea.pixels, ai, y2, x3 >> 16, x1 >> 16, l3, l1, l4, k5, j6, i5, l5, k6);
					x1 += i7;
					x3 += i8;
					l1 += j7;
					l3 += j8;
					y2 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			x3 = x2 <<= 16;
			l3 = l2 <<= 16;
			if(y2 < 0)
			{
				x3 -= i7 * y2;
				x2 -= k7 * y2;
				l3 -= j7 * y2;
				l2 -= l7 * y2;
				y2 = 0;
			}
			x1 <<= 16;
			l1 <<= 16;
			if(y1 < 0)
			{
				x1 -= i8 * y1;
				l1 -= j8 * y1;
				y1 = 0;
			}
			int j9 = y2 - centerY;
			l4 += j5 * j9;
			k5 += i6 * j9;
			j6 += l6 * j9;
			if(i7 < k7)
			{
				y3 -= y1;
				y1 -= y2;
				y2 = lineOffsets[y2];
				while(--y1 >= 0) 
				{
					drawHDTexturedScanline(DrawingArea.pixels, ai, y2, x3 >> 16, x2 >> 16, l3, l2, l4, k5, j6, i5, l5, k6);
					x3 += i7;
					x2 += k7;
					l3 += j7;
					l2 += l7;
					y2 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while(--y3 >= 0) 
				{
					drawHDTexturedScanline(DrawingArea.pixels, ai, y2, x1 >> 16, x2 >> 16, l1, l2, l4, k5, j6, i5, l5, k6);
					x1 += i8;
					x2 += k7;
					l1 += j8;
					l2 += l7;
					y2 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y3 -= y1;
			y1 -= y2;
			y2 = lineOffsets[y2];
			while(--y1 >= 0) 
			{
				drawHDTexturedScanline(DrawingArea.pixels, ai, y2, x2 >> 16, x3 >> 16, l2, l3, l4, k5, j6, i5, l5, k6);
				x3 += i7;
				x2 += k7;
				l3 += j7;
				l2 += l7;
				y2 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while(--y3 >= 0) 
			{
				drawHDTexturedScanline(DrawingArea.pixels, ai, y2, x2 >> 16, x1 >> 16, l2, l1, l4, k5, j6, i5, l5, k6);
				x1 += i8;
				x2 += k7;
				l1 += j8;
				l2 += l7;
				y2 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if(y3 >= DrawingArea.bottomY)
			return;
		if(y1 > DrawingArea.bottomY)
			y1 = DrawingArea.bottomY;
		if(y2 > DrawingArea.bottomY)
			y2 = DrawingArea.bottomY;
		if(y1 < y2)
		{
			x2 = x3 <<= 16;
			l2 = l3 <<= 16;
			if(y3 < 0)
			{
				x2 -= k7 * y3;
				x3 -= i8 * y3;
				l2 -= l7 * y3;
				l3 -= j8 * y3;
				y3 = 0;
			}
			x1 <<= 16;
			l1 <<= 16;
			if(y1 < 0)
			{
				x1 -= i7 * y1;
				l1 -= j7 * y1;
				y1 = 0;
			}
			int k9 = y3 - centerY;
			l4 += j5 * k9;
			k5 += i6 * k9;
			j6 += l6 * k9;
			if(k7 < i8)
			{
				y2 -= y1;
				y1 -= y3;
				y3 = lineOffsets[y3];
				while(--y1 >= 0) 
				{
					drawHDTexturedScanline(DrawingArea.pixels, ai, y3, x2 >> 16, x3 >> 16, l2, l3, l4, k5, j6, i5, l5, k6);
					x2 += k7;
					x3 += i8;
					l2 += l7;
					l3 += j8;
					y3 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while(--y2 >= 0) 
				{
					drawHDTexturedScanline(DrawingArea.pixels, ai, y3, x2 >> 16, x1 >> 16, l2, l1, l4, k5, j6, i5, l5, k6);
					x2 += k7;
					x1 += i7;
					l2 += l7;
					l1 += j7;
					y3 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y2 -= y1;
			y1 -= y3;
			y3 = lineOffsets[y3];
			while(--y1 >= 0) 
			{
				drawHDTexturedScanline(DrawingArea.pixels, ai, y3, x3 >> 16, x2 >> 16, l3, l2, l4, k5, j6, i5, l5, k6);
				x2 += k7;
				x3 += i8;
				l2 += l7;
				l3 += j8;
				y3 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while(--y2 >= 0) 
			{
				drawHDTexturedScanline(DrawingArea.pixels, ai, y3, x1 >> 16, x2 >> 16, l1, l2, l4, k5, j6, i5, l5, k6);
				x2 += k7;
				x1 += i7;
				l2 += l7;
				l1 += j7;
				y3 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		x1 = x3 <<= 16;
		l1 = l3 <<= 16;
		if(y3 < 0)
		{
			x1 -= k7 * y3;
			x3 -= i8 * y3;
			l1 -= l7 * y3;
			l3 -= j8 * y3;
			y3 = 0;
		}
		x2 <<= 16;
		l2 <<= 16;
		if(y2 < 0)
		{
			x2 -= i7 * y2;
			l2 -= j7 * y2;
			y2 = 0;
		}
		int l9 = y3 - centerY;
		l4 += j5 * l9;
		k5 += i6 * l9;
		j6 += l6 * l9;
		if(k7 < i8)
		{
			y1 -= y2;
			y2 -= y3;
			y3 = lineOffsets[y3];
			while(--y2 >= 0) 
			{
				drawHDTexturedScanline(DrawingArea.pixels, ai, y3, x1 >> 16, x3 >> 16, l1, l3, l4, k5, j6, i5, l5, k6);
				x1 += k7;
				x3 += i8;
				l1 += l7;
				l3 += j8;
				y3 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while(--y1 >= 0) 
			{
				drawHDTexturedScanline(DrawingArea.pixels, ai, y3, x2 >> 16, x3 >> 16, l2, l3, l4, k5, j6, i5, l5, k6);
				x2 += i7;
				x3 += i8;
				l2 += j7;
				l3 += j8;
				y3 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		y1 -= y2;
		y2 -= y3;
		y3 = lineOffsets[y3];
		while(--y2 >= 0) 
		{
			drawHDTexturedScanline(DrawingArea.pixels, ai, y3, x3 >> 16, x1 >> 16, l3, l1, l4, k5, j6, i5, l5, k6);
			x1 += k7;
			x3 += i8;
			l1 += l7;
			l3 += j8;
			y3 += DrawingArea.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
		while(--y1 >= 0) 
		{
			drawHDTexturedScanline(DrawingArea.pixels, ai, y3, x3 >> 16, x2 >> 16, l3, l2, l4, k5, j6, i5, l5, k6);
			x2 += i7;
			x3 += i8;
			l2 += j7;
			l3 += j8;
			y3 += DrawingArea.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
	}

	private static void drawHDTexturedScanline(int ai[], int ai1[], int k, int x1, int x2, int l1,
								  int l2, int a1, int i2, int j2, int k2, int a2, int i3)
	{
		int i = 0;//was parameter
		int j = 0;//was parameter
		if(x1 >= x2)
			return;
		int dl = (l2 - l1) / (x2 - x1);
		int n;
		if(aBoolean1462)
		{
			if(x2 > DrawingArea.centerX)
				x2 = DrawingArea.centerX;
			if(x1 < 0)
			{
				l1 -= x1 * dl;
				x1 = 0;
			}
		}
		if(x1 >= x2)
			return;
		n = x2 - x1 >> 3;
		k += x1;
		if(lowMem)
		{
			int i4 = 0;
			int k4 = 0;
			int k6 = x1 - centerX;
			a1 += (k2 >> 3) * k6;
			i2 += (a2 >> 3) * k6;
			j2 += (i3 >> 3) * k6;
			int i5 = j2 >> 12;
			if(i5 != 0)
			{
				i = a1 / i5;
				j = i2 / i5;
				if(i < 0)
					i = 0;
				else
				if(i > 4032)
					i = 4032;
			}
			a1 += k2;
			i2 += a2;
			j2 += i3;
			i5 = j2 >> 12;
			if(i5 != 0)
			{
				i4 = a1 / i5;
				k4 = i2 / i5;
				if(i4 < 7)
					i4 = 7;
				else
				if(i4 > 4032)
					i4 = 4032;
			}
			int i7 = i4 - i >> 3;
			int k7 = k4 - j >> 3;
			if(aBoolean1463)
			{
				int rgb;
				int l;
				while(n-- > 0) 
				{
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
					a1 += k2;
					i2 += a2;
					j2 += i3;
					int j5 = j2 >> 12;
					if(j5 != 0)
					{
						i4 = a1 / j5;
						k4 = i2 / j5;
						if(i4 < 7)
							i4 = 7;
						else
						if(i4 > 4032)
							i4 = 4032;
					}
					i7 = i4 - i >> 3;
					k7 = k4 - j >> 3;
					l1 += dl;
				}
				for(n = x2 - x1 & 7; n-- > 0;)
				{
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
				}
				return;
			}
			while(n-- > 0) 
			{
				int k8;
				int l;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
				a1 += k2;
				i2 += a2;
				j2 += i3;
				int k5 = j2 >> 12;
				if(k5 != 0)
				{
					i4 = a1 / k5;
					k4 = i2 / k5;
					if(i4 < 7)
						i4 = 7;
					else
					if(i4 > 4032)
						i4 = 4032;
				}
				i7 = i4 - i >> 3;
				k7 = k4 - j >> 3;
				l1 += dl;
			}
			for(n = x2 - x1 & 7; n-- > 0;)
			{
				int l8;
				int l;
				if((l8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((l8 & 0xff00ff) * l & ~0xff00ff) + ((l8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
			}

			return;
		}
		int j4 = 0;
		int l4 = 0;
		int l6 = x1 - centerX;
		a1 += (k2 >> 3) * l6;
		i2 += (a2 >> 3) * l6;
		j2 += (i3 >> 3) * l6;
		int l5 = j2 >> 14;
		if(l5 != 0)
		{
			i = a1 / l5;
			j = i2 / l5;
			if(i < 0)
				i = 0;
			else
			if(i > 16256)
				i = 16256;
		}
		a1 += k2;
		i2 += a2;
		j2 += i3;
		l5 = j2 >> 14;
		if(l5 != 0)
		{
			j4 = a1 / l5;
			l4 = i2 / l5;
			if(j4 < 7)
				j4 = 7;
			else
			if(j4 > 16256)
				j4 = 16256;
		}
		int j7 = j4 - i >> 3;
		int l7 = l4 - j >> 3;
		if(aBoolean1463)
		{
			while(n-- > 0) 
			{
				int rgb;
				int l;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
				a1 += k2;
				i2 += a2;
				j2 += i3;
				int i6 = j2 >> 14;
				if(i6 != 0)
				{
					j4 = a1 / i6;
					l4 = i2 / i6;
					if(j4 < 7)
						j4 = 7;
					else
					if(j4 > 16256)
						j4 = 16256;
				}
				j7 = j4 - i >> 3;
				l7 = l4 - j >> 3;
				l1 += dl;
			}
			for(n = x2 - x1 & 7; n-- > 0;)
			{
				int rgb;
				int l;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
			}

			return;
		}
		while(n-- > 0) 
		{
			int i9;
			int l;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
			a1 += k2;
			i2 += a2;
			j2 += i3;
			int j6 = j2 >> 14;
			if(j6 != 0)
			{
				j4 = a1 / j6;
				l4 = i2 / j6;
				if(j4 < 7)
					j4 = 7;
				else
				if(j4 > 16256)
					j4 = 16256;
			}
			j7 = j4 - i >> 3;
			l7 = l4 - j >> 3;
			l1 += dl;
		}
		for(int l3 = x2 - x1 & 7; l3-- > 0;)
		{
			int j9;
			int l;
			if((j9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((j9 & 0xff00ff) * l & ~0xff00ff) + ((j9 & 0xff00) * l & 0xff0000) >> 8;;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
		}

	}
	
public static void drawMaterializedTriangle(int y1, int y2, int y3, int x1, int x2, int x3, int hsl1, int hsl2, int hsl3, int tx1, int tx2, int tx3, int ty1, int ty2, int ty3, int tz1, int tz2, int tz3, int tex) {
		if (!Configuration.hdTexturing || Texture.get(tex) == null) {
			drawGouraudTriangle(y1, y2, y3, x1, x2, x3, hsl1, hsl2, hsl3);
			return;
		}
		int area = x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2) >> 1;
		if (area < 0) {
			area = -area;
		}
		if (area > 4096) {
			textureMipmap = 1;
		} else if (area > 1024) {
			textureMipmap = 1;
		} else if (area > 256) {
			textureMipmap = 2;
		} else if (area > 64) {
			textureMipmap = 3;
		} else if (area > 16) {
			textureMipmap = 4;
		} else if (area > 4) {
			textureMipmap = 5;
		} else if (area > 1) {
			textureMipmap = 6;
		} else {
			textureMipmap = 7;
		}
		int[] ai = Texture.get(tex).mipmaps[textureMipmap];
		//int[] ai = method371(tex);
		//System.out.println(ai);
		tx2 = tx1 - tx2;
		ty2 = ty1 - ty2;
		tz2 = tz1 - tz2;
		tx3 -= tx1;
		ty3 -= ty1;
		tz3 -= tz1;
		int l4 = tx3 * ty1 - ty3 * tx1 << (WorldController.viewDistance == 9 ? 14 : 15);
		int i5 = ty3 * tz1 - tz3 * ty1 << 8;
		int j5 = tz3 * tx1 - tx3 * tz1 << 5;
		int k5 = tx2 * ty1 - ty2 * tx1 << (WorldController.viewDistance == 9 ? 14 : 15);
		int l5 = ty2 * tz1 - tz2 * ty1 << 8;
		int i6 = tz2 * tx1 - tx2 * tz1 << 5;
		int j6 = ty2 * tx3 - tx2 * ty3 << (WorldController.viewDistance == 9 ? 14 : 15);
		int k6 = tz2 * ty3 - ty2 * tz3 << 8;
		int l6 = tx2 * tz3 - tz2 * tx3 << 5;
		int i7 = 0;
		int j7 = 0;
		if (y2 != y1) {
			i7 = (x2 - x1 << 16) / (y2 - y1);
			j7 = (hsl2 - hsl1 << 15) / (y2 - y1);
		}
		int k7 = 0;
		int l7 = 0;
		if (y3 != y2) {
			k7 = (x3 - x2 << 16) / (y3 - y2);
			l7 = (hsl3 - hsl2 << 15) / (y3 - y2);
		}
		int i8 = 0;
		int j8 = 0;
		if (y3 != y1) {
			i8 = (x1 - x3 << 16) / (y1 - y3);
			j8 = (hsl1 - hsl3 << 15) / (y1 - y3);
		}
		if (y1 <= y2 && y1 <= y3) {
			if (y1 >= DrawingArea.bottomY) {
				return;
			}
			if (y2 > DrawingArea.bottomY) {
				y2 = DrawingArea.bottomY;
			}
			if (y3 > DrawingArea.bottomY) {
				y3 = DrawingArea.bottomY;
			}
			if (y2 < y3) {
				x3 = x1 <<= 16;
				hsl3 = hsl1 <<= 15;
				if (y1 < 0) {
					x3 -= i8 * y1;
					x1 -= i7 * y1;
					hsl3 -= j8 * y1;
					hsl1 -= j7 * y1;
					y1 = 0;
				}
				x2 <<= 16;
				hsl2 <<= 15;
				if (y2 < 0) {
					x2 -= k7 * y2;
					hsl2 -= l7 * y2;
					y2 = 0;
				}
				int k8 = y1 - centerY;
				l4 += j5 * k8;
				k5 += i6 * k8;
				j6 += l6 * k8;
				if (y1 != y2 && i8 < i7 || y1 == y2 && i8 > k7) {
					y3 -= y2;
					y2 -= y1;
					y1 = lineOffsets[y1];
					while (--y2 >= 0) {
						drawMaterializedScanline(DrawingArea.pixels, ai, y1, x3 >> 16, x1 >> 16, hsl3 >> 7, hsl1 >> 7, l4, k5, j6, i5, l5, k6);
						x3 += i8;
						x1 += i7;
						hsl3 += j8;
						hsl1 += j7;
						y1 += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while (--y3 >= 0) {
						drawMaterializedScanline(DrawingArea.pixels, ai, y1, x3 >> 16, x2 >> 16, hsl3 >> 7, hsl2 >> 7, l4, k5, j6, i5, l5, k6);
						x3 += i8;
						x2 += k7;
						hsl3 += j8;
						hsl2 += l7;
						y1 += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				y3 -= y2;
				y2 -= y1;
				y1 = lineOffsets[y1];
				while (--y2 >= 0) {
					drawMaterializedScanline(DrawingArea.pixels, ai, y1, x1 >> 16, x3 >> 16, hsl1 >> 7, hsl3 >> 7, l4, k5, j6, i5, l5, k6);
					x3 += i8;
					x1 += i7;
					hsl3 += j8;
					hsl1 += j7;
					y1 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y3 >= 0) {
					drawMaterializedScanline(DrawingArea.pixels, ai, y1, x2 >> 16, x3 >> 16, hsl2 >> 7, hsl3 >> 7, l4, k5, j6, i5, l5, k6);
					x3 += i8;
					x2 += k7;
					hsl3 += j8;
					hsl2 += l7;
					y1 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			x2 = x1 <<= 16;
			hsl2 = hsl1 <<= 15;
			if (y1 < 0) {
				x2 -= i8 * y1;
				x1 -= i7 * y1;
				hsl2 -= j8 * y1;
				hsl1 -= j7 * y1;
				y1 = 0;
			}
			x3 <<= 16;
			hsl3 <<= 15;
			if (y3 < 0) {
				x3 -= k7 * y3;
				hsl3 -= l7 * y3;
				y3 = 0;
			}
			int l8 = y1 - centerY;
			l4 += j5 * l8;
			k5 += i6 * l8;
			j6 += l6 * l8;
			if (y1 != y3 && i8 < i7 || y1 == y3 && k7 > i7) {
				y2 -= y3;
				y3 -= y1;
				y1 = lineOffsets[y1];
				while (--y3 >= 0) {
					drawMaterializedScanline(DrawingArea.pixels, ai, y1, x2 >> 16, x1 >> 16, hsl2 >> 7, hsl1 >> 7, l4, k5, j6, i5, l5, k6);
					x2 += i8;
					x1 += i7;
					hsl2 += j8;
					hsl1 += j7;
					y1 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y2 >= 0) {
					drawMaterializedScanline(DrawingArea.pixels, ai, y1, x3 >> 16, x1 >> 16, hsl3 >> 7, hsl1 >> 7, l4, k5, j6, i5, l5, k6);
					x3 += k7;
					x1 += i7;
					hsl3 += l7;
					hsl1 += j7;
					y1 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y2 -= y3;
			y3 -= y1;
			y1 = lineOffsets[y1];
			while (--y3 >= 0) {
				drawMaterializedScanline(DrawingArea.pixels, ai, y1, x1 >> 16, x2 >> 16, hsl1 >> 7, hsl2 >> 7, l4, k5, j6, i5, l5, k6);
				x2 += i8;
				x1 += i7;
				hsl2 += j8;
				hsl1 += j7;
				y1 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y2 >= 0) {
				drawMaterializedScanline(DrawingArea.pixels, ai, y1, x1 >> 16, x3 >> 16, hsl1 >> 7, hsl3 >> 7, l4, k5, j6, i5, l5, k6);
				x3 += k7;
				x1 += i7;
				hsl3 += l7;
				hsl1 += j7;
				y1 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if (y2 <= y3) {
			if (y2 >= DrawingArea.bottomY) {
				return;
			}
			if (y3 > DrawingArea.bottomY) {
				y3 = DrawingArea.bottomY;
			}
			if (y1 > DrawingArea.bottomY) {
				y1 = DrawingArea.bottomY;
			}
			if (y3 < y1) {
				x1 = x2 <<= 16;
				hsl1 = hsl2 <<= 15;
				if (y2 < 0) {
					x1 -= i7 * y2;
					x2 -= k7 * y2;
					hsl1 -= j7 * y2;
					hsl2 -= l7 * y2;
					y2 = 0;
				}
				x3 <<= 16;
				hsl3 <<= 15;
				if (y3 < 0) {
					x3 -= i8 * y3;
					hsl3 -= j8 * y3;
					y3 = 0;
				}
				int i9 = y2 - centerY;
				l4 += j5 * i9;
				k5 += i6 * i9;
				j6 += l6 * i9;
				if (y2 != y3 && i7 < k7 || y2 == y3 && i7 > i8) {
					y1 -= y3;
					y3 -= y2;
					y2 = lineOffsets[y2];
					while (--y3 >= 0) {
						drawMaterializedScanline(DrawingArea.pixels, ai, y2, x1 >> 16, x2 >> 16, hsl1 >> 7, hsl2 >> 7, l4, k5, j6, i5, l5, k6);
						x1 += i7;
						x2 += k7;
						hsl1 += j7;
						hsl2 += l7;
						y2 += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while (--y1 >= 0) {
						drawMaterializedScanline(DrawingArea.pixels, ai, y2, x1 >> 16, x3 >> 16, hsl1 >> 7, hsl3 >> 7, l4, k5, j6, i5, l5, k6);
						x1 += i7;
						x3 += i8;
						hsl1 += j7;
						hsl3 += j8;
						y2 += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				y1 -= y3;
				y3 -= y2;
				y2 = lineOffsets[y2];
				while (--y3 >= 0) {
					drawMaterializedScanline(DrawingArea.pixels, ai, y2, x2 >> 16, x1 >> 16, hsl2 >> 7, hsl1 >> 7, l4, k5, j6, i5, l5, k6);
					x1 += i7;
					x2 += k7;
					hsl1 += j7;
					hsl2 += l7;
					y2 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y1 >= 0) {
					drawMaterializedScanline(DrawingArea.pixels, ai, y2, x3 >> 16, x1 >> 16, hsl3 >> 7, hsl1 >> 7, l4, k5, j6, i5, l5, k6);
					x1 += i7;
					x3 += i8;
					hsl1 += j7;
					hsl3 += j8;
					y2 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			x3 = x2 <<= 16;
			hsl3 = hsl2 <<= 15;
			if (y2 < 0) {
				x3 -= i7 * y2;
				x2 -= k7 * y2;
				hsl3 -= j7 * y2;
				hsl2 -= l7 * y2;
				y2 = 0;
			}
			x1 <<= 16;
			hsl1 <<= 15;
			if (y1 < 0) {
				x1 -= i8 * y1;
				hsl1 -= j8 * y1;
				y1 = 0;
			}
			int j9 = y2 - centerY;
			l4 += j5 * j9;
			k5 += i6 * j9;
			j6 += l6 * j9;
			if (i7 < k7) {
				y3 -= y1;
				y1 -= y2;
				y2 = lineOffsets[y2];
				while (--y1 >= 0) {
					drawMaterializedScanline(DrawingArea.pixels, ai, y2, x3 >> 16, x2 >> 16, hsl3 >> 7, hsl2 >> 7, l4, k5, j6, i5, l5, k6);
					x3 += i7;
					x2 += k7;
					hsl3 += j7;
					hsl2 += l7;
					y2 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y3 >= 0) {
					drawMaterializedScanline(DrawingArea.pixels, ai, y2, x1 >> 16, x2 >> 16, hsl1 >> 7, hsl2 >> 7, l4, k5, j6, i5, l5, k6);
					x1 += i8;
					x2 += k7;
					hsl1 += j8;
					hsl2 += l7;
					y2 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y3 -= y1;
			y1 -= y2;
			y2 = lineOffsets[y2];
			while (--y1 >= 0) {
				drawMaterializedScanline(DrawingArea.pixels, ai, y2, x2 >> 16, x3 >> 16, hsl2 >> 7, hsl3 >> 7, l4, k5, j6, i5, l5, k6);
				x3 += i7;
				x2 += k7;
				hsl3 += j7;
				hsl2 += l7;
				y2 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y3 >= 0) {
				drawMaterializedScanline(DrawingArea.pixels, ai, y2, x2 >> 16, x1 >> 16, hsl2 >> 7, hsl1 >> 7, l4, k5, j6, i5, l5, k6);
				x1 += i8;
				x2 += k7;
				hsl1 += j8;
				hsl2 += l7;
				y2 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if (y3 >= DrawingArea.bottomY) {
			return;
		}
		if (y1 > DrawingArea.bottomY) {
			y1 = DrawingArea.bottomY;
		}
		if (y2 > DrawingArea.bottomY) {
			y2 = DrawingArea.bottomY;
		}
		if (y1 < y2) {
			x2 = x3 <<= 16;
			hsl2 = hsl3 <<= 15;
			if (y3 < 0) {
				x2 -= k7 * y3;
				x3 -= i8 * y3;
				hsl2 -= l7 * y3;
				hsl3 -= j8 * y3;
				y3 = 0;
			}
			x1 <<= 16;
			hsl1 <<= 15;
			if (y1 < 0) {
				x1 -= i7 * y1;
				hsl1 -= j7 * y1;
				y1 = 0;
			}
			int k9 = y3 - centerY;
			l4 += j5 * k9;
			k5 += i6 * k9;
			j6 += l6 * k9;
			if (k7 < i8) {
				y2 -= y1;
				y1 -= y3;
				y3 = lineOffsets[y3];
				while (--y1 >= 0) {
					drawMaterializedScanline(DrawingArea.pixels, ai, y3, x2 >> 16, x3 >> 16, hsl2 >> 7, hsl3 >> 7, l4, k5, j6, i5, l5, k6);
					x2 += k7;
					x3 += i8;
					hsl2 += l7;
					hsl3 += j8;
					y3 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y2 >= 0) {
					drawMaterializedScanline(DrawingArea.pixels, ai, y3, x2 >> 16, x1 >> 16, hsl2 >> 7, hsl1 >> 7, l4, k5, j6, i5, l5, k6);
					x2 += k7;
					x1 += i7;
					hsl2 += l7;
					hsl1 += j7;
					y3 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y2 -= y1;
			y1 -= y3;
			y3 = lineOffsets[y3];
			while (--y1 >= 0) {
				drawMaterializedScanline(DrawingArea.pixels, ai, y3, x3 >> 16, x2 >> 16, hsl3 >> 7, hsl2 >> 7, l4, k5, j6, i5, l5, k6);
				x2 += k7;
				x3 += i8;
				hsl2 += l7;
				hsl3 += j8;
				y3 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y2 >= 0) {
				drawMaterializedScanline(DrawingArea.pixels, ai, y3, x1 >> 16, x2 >> 16, hsl1 >> 7, hsl2 >> 7, l4, k5, j6, i5, l5, k6);
				x2 += k7;
				x1 += i7;
				hsl2 += l7;
				hsl1 += j7;
				y3 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		x1 = x3 <<= 16;
		hsl1 = hsl3 <<= 15;
		if (y3 < 0) {
			x1 -= k7 * y3;
			x3 -= i8 * y3;
			hsl1 -= l7 * y3;
			hsl3 -= j8 * y3;
			y3 = 0;
		}
		x2 <<= 16;
		hsl2 <<= 15;
		if (y2 < 0) {
			x2 -= i7 * y2;
			hsl2 -= j7 * y2;
			y2 = 0;
		}
		int l9 = y3 - centerY;
		l4 += j5 * l9;
		k5 += i6 * l9;
		j6 += l6 * l9;
		if (k7 < i8) {
			y1 -= y2;
			y2 -= y3;
			y3 = lineOffsets[y3];
			while (--y2 >= 0) {
				drawMaterializedScanline(DrawingArea.pixels, ai, y3, x1 >> 16, x3 >> 16, hsl1 >> 7, hsl3 >> 7, l4, k5, j6, i5, l5, k6);
				x1 += k7;
				x3 += i8;
				hsl1 += l7;
				hsl3 += j8;
				y3 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y1 >= 0) {
				drawMaterializedScanline(DrawingArea.pixels, ai, y3, x2 >> 16, x3 >> 16, hsl2 >> 7, hsl3 >> 7, l4, k5, j6, i5, l5, k6);
				x2 += i7;
				x3 += i8;
				hsl2 += j7;
				hsl3 += j8;
				y3 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		y1 -= y2;
		y2 -= y3;
		y3 = lineOffsets[y3];
		while (--y2 >= 0) {
			drawMaterializedScanline(DrawingArea.pixels, ai, y3, x3 >> 16, x1 >> 16, hsl3 >> 7, hsl1 >> 7, l4, k5, j6, i5, l5, k6);
			x1 += k7;
			x3 += i8;
			hsl1 += l7;
			hsl3 += j8;
			y3 += DrawingArea.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
		while (--y1 >= 0) {
			drawMaterializedScanline(DrawingArea.pixels, ai, y3, x3 >> 16, x2 >> 16, hsl3 >> 7, hsl2 >> 7, l4, k5, j6, i5, l5, k6);
			x2 += i7;
			x3 += i8;
			hsl2 += j7;
			hsl3 += j8;
			y3 += DrawingArea.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
	}

	private static void drawMaterializedScanline(int dst[], int src[], int off, int x1, int x2, int hsl1, int hsl2, int l1, int i2, int j2, int k2, int l2, int i3) {
		int i = 0;// was parameter
		int j = 0;// was parameter
		if (x1 >= x2) {
			return;
		}
		int k3;
		hsl2 = (hsl2 - hsl1) / (x2 - x1);
		if (aBoolean1462) {
			if (x2 > DrawingArea.centerX) {
				x2 = DrawingArea.centerX;
			}
			if (x1 < 0) {
				hsl1 -= x1 * hsl2;
				x1 = 0;
			}
			if (x1 >= x2) {
				return;
			}
			k3 = x2 - x1 >> 3;
		} else {
			if (x2 - x1 > 7) {
				k3 = x2 - x1 >> 3;
			} else {
				k3 = 0;
			}
		}
		off += x1;
		int j4 = 0;
		int l4 = 0;
		int l6 = x1 - centerX;
		l1 += (k2 >> 3) * l6;
		i2 += (l2 >> 3) * l6;
		j2 += (i3 >> 3) * l6;
		int l5 = j2 >> 14;
		if (l5 != 0) {
			i = l1 / l5;
			j = i2 / l5;
			if (i < 0) {
				i = 0;
			} else if (i > 16256) {
				i = 16256;
			}
		}
		l1 += k2;
		i2 += l2;
		j2 += i3;
		l5 = j2 >> 14;
		if (l5 != 0) {
			j4 = l1 / l5;
			l4 = i2 / l5;
			if (j4 < 7) {
				j4 = 7;
			} else if (j4 > 16256) {
				j4 = 16256;
			}
		}
		int j7 = j4 - i >> 3;
		int l7 = l4 - j >> 3;
		int rgb1, rgb2;
		while (k3-- > 0) {
			rgb1 = anIntArray1482[hsl1 >> 8];
			rgb2 = src[texelPos((j & 0x3f80) + (i >> 7))];
			dst[off++] = (((rgb1 >> 16 & 0xff) * (rgb2 >> 17 & 0x7f) << 11) / 3 & 0xff0000) + (((rgb1 >> 8 & 0xff) * (rgb2 >> 9 & 0x7f) << 3) / 3 & 0xff00) + (((rgb1 & 0xff) * (rgb2 >> 1 & 0x7f) >> 5) / 3 & 0xff);
			i += j7;
			j += l7;
			hsl1 += hsl2;
			rgb1 = anIntArray1482[hsl1 >> 8];
			rgb2 = src[texelPos((j & 0x3f80) + (i >> 7))];
			dst[off++] = (((rgb1 >> 16 & 0xff) * (rgb2 >> 17 & 0x7f) << 11) / 3 & 0xff0000) + (((rgb1 >> 8 & 0xff) * (rgb2 >> 9 & 0x7f) << 3) / 3 & 0xff00) + (((rgb1 & 0xff) * (rgb2 >> 1 & 0x7f) >> 5) / 3 & 0xff);
			i += j7;
			j += l7;
			hsl1 += hsl2;
			rgb1 = anIntArray1482[hsl1 >> 8];
			rgb2 = src[texelPos((j & 0x3f80) + (i >> 7))];
			dst[off++] = (((rgb1 >> 16 & 0xff) * (rgb2 >> 17 & 0x7f) << 11) / 3 & 0xff0000) + (((rgb1 >> 8 & 0xff) * (rgb2 >> 9 & 0x7f) << 3) / 3 & 0xff00) + (((rgb1 & 0xff) * (rgb2 >> 1 & 0x7f) >> 5) / 3 & 0xff);
			i += j7;
			j += l7;
			hsl1 += hsl2;
			rgb1 = anIntArray1482[hsl1 >> 8];
			rgb2 = src[texelPos((j & 0x3f80) + (i >> 7))];
			dst[off++] = (((rgb1 >> 16 & 0xff) * (rgb2 >> 17 & 0x7f) << 11) / 3 & 0xff0000) + (((rgb1 >> 8 & 0xff) * (rgb2 >> 9 & 0x7f) << 3) / 3 & 0xff00) + (((rgb1 & 0xff) * (rgb2 >> 1 & 0x7f) >> 5) / 3 & 0xff);
			i += j7;
			j += l7;
			hsl1 += hsl2;
			rgb1 = anIntArray1482[hsl1 >> 8];
			rgb2 = src[texelPos((j & 0x3f80) + (i >> 7))];
			dst[off++] = (((rgb1 >> 16 & 0xff) * (rgb2 >> 17 & 0x7f) << 11) / 3 & 0xff0000) + (((rgb1 >> 8 & 0xff) * (rgb2 >> 9 & 0x7f) << 3) / 3 & 0xff00) + (((rgb1 & 0xff) * (rgb2 >> 1 & 0x7f) >> 5) / 3 & 0xff);
			i += j7;
			j += l7;
			hsl1 += hsl2;
			rgb1 = anIntArray1482[hsl1 >> 8];
			rgb2 = src[texelPos((j & 0x3f80) + (i >> 7))];
			dst[off++] = (((rgb1 >> 16 & 0xff) * (rgb2 >> 17 & 0x7f) << 11) / 3 & 0xff0000) + (((rgb1 >> 8 & 0xff) * (rgb2 >> 9 & 0x7f) << 3) / 3 & 0xff00) + (((rgb1 & 0xff) * (rgb2 >> 1 & 0x7f) >> 5) / 3 & 0xff);
			i += j7;
			j += l7;
			hsl1 += hsl2;
			rgb1 = anIntArray1482[hsl1 >> 8];
			rgb2 = src[texelPos((j & 0x3f80) + (i >> 7))];
			dst[off++] = (((rgb1 >> 16 & 0xff) * (rgb2 >> 17 & 0x7f) << 11) / 3 & 0xff0000) + (((rgb1 >> 8 & 0xff) * (rgb2 >> 9 & 0x7f) << 3) / 3 & 0xff00) + (((rgb1 & 0xff) * (rgb2 >> 1 & 0x7f) >> 5) / 3 & 0xff);
			i += j7;
			j += l7;
			hsl1 += hsl2;
			rgb1 = anIntArray1482[hsl1 >> 8];
			rgb2 = src[texelPos((j & 0x3f80) + (i >> 7))];
			dst[off++] = (((rgb1 >> 16 & 0xff) * (rgb2 >> 17 & 0x7f) << 11) / 3 & 0xff0000) + (((rgb1 >> 8 & 0xff) * (rgb2 >> 9 & 0x7f) << 3) / 3 & 0xff00) + (((rgb1 & 0xff) * (rgb2 >> 1 & 0x7f) >> 5) / 3 & 0xff);
			i = j4;
			j = l4;
			hsl1 += hsl2;
			l1 += k2;
			i2 += l2;
			j2 += i3;
			int i6 = j2 >> 14;
			if (i6 != 0) {
				j4 = l1 / i6;
				l4 = i2 / i6;
				if (j4 < 7) {
					j4 = 7;
				} else if (j4 > 16256) {
					j4 = 16256;
				}
			}
			j7 = j4 - i >> 3;
			l7 = l4 - j >> 3;
		}
		for (k3 = x2 - x1 & 7; k3-- > 0;) {
			rgb1 = anIntArray1482[hsl1 >> 8];
			rgb2 = src[texelPos((j & 0x3f80) + (i >> 7))];
			dst[off++] = (((rgb1 >> 16 & 0xff) * (rgb2 >> 17 & 0x7f) << 11) / 3 & 0xff0000) + (((rgb1 >> 8 & 0xff) * (rgb2 >> 9 & 0x7f) << 3) / 3 & 0xff00) + (((rgb1 & 0xff) * (rgb2 >> 1 & 0x7f) >> 5) / 3 & 0xff);
			i += j7;
			j += l7;
			hsl1 += hsl2;
		}
	}
	
	public static void setBounds(int j, int k) {
		lineOffsets = new int[k];
		for(int l = 0; l < k; l++)
			lineOffsets[l] = j * l;

		centerX = j / 2;
		centerY = k / 2;
	}
	
	public static void setDefaultBounds() {
		setBounds(width, height);
	}


	private static int textureMipmap;
//by any chance do you have a backup of after texturing ah sec
	private static int texelPos(int defaultIndex) {
		int x = defaultIndex & 127;
		int y = defaultIndex >> 7;
		x >>= textureMipmap;
		y >>= textureMipmap;
		return x + (y << 7 - textureMipmap);
	}


	public static final int anInt1459 = -477;
	public static boolean lowMem = true;
	public static boolean aBoolean1462;
	
	private static boolean aBoolean1463;
	public static boolean aBoolean1464 = true;
	public static int anInt1465;
	public static int centerX;
	public static int centerY;
	public static int textureAmount = 60;
	private static int[] anIntArray1468;
	public static final int[] anIntArray1469;
	public static int anIntArray1470[];
	public static int anIntArray1471[];
	public static int lineOffsets[];
	public static int SINE[];
	public static int COSINE[];
	private static int anInt1473;
	public static Background aBackgroundArray1474s[] = new Background[textureAmount];
	private static boolean[] aBooleanArray1475 = new boolean[textureAmount];
	private static int[] anIntArray1476 = new int[textureAmount];
	private static int anInt1477;
	private static int[][] anIntArrayArray1478;
	private static int[][] anIntArrayArray1479 = new int[textureAmount][];
	public static int anIntArray1480[] = new int[textureAmount];
	public static int anInt1481;
	public static int anIntArray1482[] = new int[0x10000];
	private static int[][] anIntArrayArray1483 = new int[textureAmount][];
	public static boolean notTextured = true;

	static 
	{
		anIntArray1468 = new int[512];
		anIntArray1469 = new int[2048];
		anIntArray1470 = new int[2048];
		anIntArray1471 = new int[2048];
		for(int i = 1; i < 512; i++)
			anIntArray1468[i] = 32768 / i;

		for(int j = 1; j < 2048; j++)
			anIntArray1469[j] = 0x10000 / j;

		for(int k = 0; k < 2048; k++)
		{
			anIntArray1470[k] = (int)(65536D * Math.sin((double)k * 0.0030679614999999999D));
			anIntArray1471[k] = (int)(65536D * Math.cos((double)k * 0.0030679614999999999D));
		}

	}
}