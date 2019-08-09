package com.nekokittygames.thaumictinkerer.common.recipes;

import thaumcraft.api.crafting.Part;

public class TTMatrix {
    int rows;
    int cols;
    Part[][] matrix;

    public TTMatrix(Part[][] matrix) {
        this.rows = matrix.length;
        this.cols = matrix[0].length;
        this.matrix = new Part[this.rows][this.cols];

        for(int i = 0; i < this.rows; ++i) {
            for(int j = 0; j < this.cols; ++j) {
                this.matrix[i][j] = matrix[i][j];
            }
        }

    }

    public void Rotate90DegRight(int times) {
        for(int a = 0; a < times; ++a) {
            Part[][] newMatrix = new Part[this.cols][this.rows];

            int i;
            for(i = 0; i < this.rows; ++i) {
                for(int j = 0; j < this.cols; ++j) {
                    newMatrix[j][this.rows - i - 1] = this.matrix[i][j];
                }
            }

            this.matrix = newMatrix;
            i = this.rows;
            this.rows = this.cols;
            this.cols = i;
        }

    }

    public Part[][] getMatrix() {
        return this.matrix;
    }
}
