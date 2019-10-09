
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/time.h>
#include <sys/resource.h>
#include <assert.h>

#include "cities.h"
#include "elapsed_time.h"

/**\mainpage Descrição
 *Este projecto foi desenvolvido com a linguagem C\n
 *Foi desenvolvido com o objetivo de calcular a distância 
 percorrida por um caixeiro viajante que tem que percorrer varias cidades e regressar aquela em que começou
 */
static int min_length,max_length, length, minlength, maxlength;
static int min_tour[max_n_cities + 1],max_tour[max_n_cities + 1];
static long n_tours;
static int maxtour[max_n_cities + 1], mintour[max_n_cities + 1];
static long hist[10001];
static long distCalc[19];

/**
  *
  *Função responsável por calcular a distância percorrida pelo traveling merchant
  *@param n: número de cidades percorridas pelo caixeiro viajante 
  *@param m: cidade inicial
  *@param a: array com a lista de cidades a percorrer
  */
void tsp_v1(int n,int m,int *a){
  int i,t;
  if(m < n - 1)
    for(i = m;i < n;i++){
      t = a[m];
      a[m] = a[i];
      a[i] = t;
      tsp_v1(n,m + 1,a);
      t = a[m];
      a[m] = a[i];
      a[i] = t;
    }
  else{ // visit permutation
    n_tours++;
    // modify the following code to do your stuff
    length =  0;
    //printf("=================\n");
    for(i = 0;i < n;i++){
    	//printf("%d%s",a[i],(i == n - 1) ? "\n" : " "); //2º*/
    	if(i != n-1){
    		length += cities[a[i]].distance[a[i+1]];
        //printf("--->%d = %d\n",i, length);
    	}    	
    	else{
    		length += cities[a[i]].distance[a[0]];
    	}
    }
    hist[length]++;
    if(length < min_length){
    	min_length = length;
    	for(i = 0;i < n;i++){
    		min_tour[i] = a[i];
    	}
    }
    if(length > max_length){
    	max_length = length;
    	for(i = 0; i < n; i++){
    		max_tour[i] = a[i];
    	}
    }
  }
}


//
// main program
//


/**
 *
 * Função Main\n
 * ---->Fornece a informação à função tsp_v1 para calcular a distância percurrida\n
 * ---->Imprime os valores calculados\n
 *@param argc: contador de argumentos ao iniciar o programa
 *@param argv: array de argumentos usados para iniciar o programa
 */
int main(int argc,char **argv)
{
  FILE *fp = fopen("tspGrafInfo", "w");
  int n_mec,special,n,i,a[max_n_cities];
  char file_name[32];
  double dt1, dt2;

  n_mec = 89069; // CHANGE THIS!
  special = 1;
  init_cities_data(n_mec,special);
  printf("data for init_cities_data(%d,%d)\n",n_mec,special); //1º
  fflush(stdout);
#if 1
  //print_distances();
#endif
  for(n = 3;n <= n_cities;n++)
  {
    //
    // try tsp_v1
    //
    dt1 = -1.0;
    dt2 = 0;
    if(n <= 15)
    {
      (void)elapsed_time();
      for(i = 0;i < n;i++)
        a[i] = i;
      min_length = minlength = 1000000000;
      max_length = maxlength = 0;
      n_tours = 0l;
      tsp_v1(n,1,a); // no need to change the starting city, as we are making a tour
      dt1 = elapsed_time();
      printf("tsp_v1() finished in %8.3fs (%ld tours generated)\n",dt1,n_tours); //3º
      printf("  min %5d [",min_length); //4º
      for(i = 0;i < n;i++)//4º
        printf("%2d%s",min_tour[i],(i == n - 1) ? "]\n" : ",");//4º
      printf("  max %5d [",max_length);
      for(i = 0;i < n;i++)
       printf("%2d%s",max_tour[i],(i == n - 1) ? "]\n" : ",");
   		distCalc[n] = dt1;
      fflush(stdout);
      if(argc == 2 && strcmp(argv[1],"-f") == 0)
      {
        min_tour[n] = -1;
        sprintf(file_name,"min_%02d.svg",n);
        make_map(file_name,min_tour);
        max_tour[n] = -1;
        sprintf(file_name,"max_%02d.svg",n);
        make_map(file_name,max_tour);
      }
    }
  }
  for(int i = 0; i < 16; i++){
  		fprintf(fp, "%d %ld\n",i, distCalc[i]);
  }
  fclose(fp);
  return 0;
}
