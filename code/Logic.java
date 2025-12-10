/*
 * Logic.java    Version 3.0    14 Feb 2025
 *
 * Copyright (c) 2025 Bridget Mendez.
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Bridget Mendez ("Confidential Information"). You shall not disclose
 * such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with
 * Bridget Mendez.
 *
 * BRIDGET MENDEZ MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE
 * SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. BRIDGET
 * MENDEZ SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING, OR DISTRIBUTING THIS SOFTWARE OR
 * ITS DERIVATIVES.
 */

import java.util.Scanner;

/**
 * Clase que controla la interacción con el usuario.
 * Presenta un menú con dos opciones:
 *  1. Integración rápida (pocas iteraciones).
 *  2. Integración profunda (muchas iteraciones).
 *
 * Esta clase centraliza la captura de datos y la
 * invocación del algoritmo de Simpson.
 */
public class Logic {

    /**
     * Número inicial de segmentos para la Regla de Simpson.
     * Debe comenzar en un número par.
     */
    private int intNumSeg;

    /**
     * Error permitido para determinar cuándo se detiene
     * el refinamiento del método de Simpson.
     */
    private double dblE;

    /**
     * Grados de libertad para la distribución t.
     */
    private int intDOF;

    /**
     * Valor límite superior de la integración.
     */
    private double dblX;

    /**
     * Constructor por defecto.
     * Define valores base de inicio.
     */
    public Logic() {
        this.intNumSeg = 10;
        this.dblE = 1E-5; /* valor por defecto (rápido) */
    }

    /**
     * Método principal indicado en el UML: logic1a().
     * Muestra el menú, solicita al usuario los datos y
     * ejecuta la integración según la opción seleccionada.
     */
    public void logic1a() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== MENU DE INTEGRACION ===");
        System.out.println("1. Integración rápida (pocas iteraciones)");
        System.out.println("2. Integración profunda (muchas iteraciones)");
        System.out.print("Selecciona una opcion: ");

        int opcion = scanner.nextInt();

        System.out.print("Introduce el valor de x: ");
        this.dblX = scanner.nextDouble();

        System.out.print("Introduce los grados de libertad (dof): ");
        this.intDOF = scanner.nextInt();

        if (opcion == 1) {

            /* Integración rápida */
            this.dblE = 1E-5;
            System.out.println("\nModo seleccionado: Integración rápida");

        } else if (opcion == 2) {

            /* Integración profunda */
            this.dblE = 1E-15;
            System.out.println("\nModo seleccionado: Integración profunda");

        } else {

            System.out.println("Opción inválida. Saliendo del programa.");
            scanner.close();
            return;
        }

        System.out.println("error permitido = " + this.dblE);
        System.out.println();

        SimpsonIntegration simpson =
                new SimpsonIntegration(
                    this.intNumSeg,
                    this.dblE,
                    this.intDOF,
                    this.dblX
                );

        /* Ejecutar refinamiento con impresión */
        double resultado = simpson.integrateWithDebug();

        /* Mostrar valor final */
        System.out.printf(
            "x = %.4f    dof = %d    p = %.5f%n",
            this.dblX,
            this.intDOF,
            resultado
        );

        /* Guardar resultado en archivo */
        Output out = new Output();
        String texto = "x=" + this.dblX +
                       ", dof=" + this.intDOF +
                       ", p=" + resultado;
        out.writeData("resultado.txt", texto);

        scanner.close();
    }
}
