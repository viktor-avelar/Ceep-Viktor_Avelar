package br.com.alura.ceep.model;

import android.graphics.Color;

import java.util.Arrays;
import java.util.List;

public class Cor {

        private int azul = Color.parseColor("#408EC9");
        private int branco = Color.parseColor("#FFFFFF");
        private int vermelho = Color.parseColor("#EC2F4B");
        private int verde = Color.parseColor("#9ACD32");
        private int amarelo = Color.parseColor("#f9f256");
        private int lilas = Color.parseColor("#f1cbff");
        private int cinza = Color.parseColor("#d2d4dc");
        private int marrom = Color.parseColor("#a47c48");
        private int roxo = Color.parseColor("#be29ec");


        public List<Integer> ListaDeCores = Arrays.asList(
                azul, branco, vermelho,
                verde, amarelo, lilas,
                cinza, marrom, roxo);

}