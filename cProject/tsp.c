//
// Student name
// Student name
// ...
//
// AED, 2018/2019
//
// solution of the traveling salesman problem
//
//./tsp -f to draw maps!
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/time.h>
#include <sys/resource.h>
#include <assert.h>

#include "cities.h"
#include "elapsed_time.h"


//
// record best solutions
//

static int min_length,max_length, length, minlength, maxlength;
static int min_tour[max_n_cities + 1],max_tour[max_n_cities + 1];
static long n_tours;
static int maxtour[max_n_cities + 1], mintour[max_n_cities + 1];
static long hist[10001];
static long distCalc[19];

//
// first solution (brute force, distance computed at the end, compute best and worst tours)
//


void rand_perm(int n, int a[]){
	int i, j, k;
	a[0] = 1;
	length = 0;
	while(a[0] != 0){
		for(i = 0; i < n; i++)
			a[i] = i;
		for(i = n-1; i > 0; i--){
			j = (int)floor((double)(i + 1)*(double)rand()/(1.0 + (double)RAND_MAX));
			assert(j >= 0 && j <= i);
			k = a[i];
			a[i] = a[j];
			a[j] = k;
		}
	}
	for(i = 0;i < n;i++){
    	//printf("%d%s",a[i],(i == n - 1) ? "\n" : " "); //2º*/
    	if(i != n-1){
    		length += cities[a[i]].distance[a[i+1]];
    	}    	
    	else{
    		length += cities[a[i]].distance[a[0]];
    	}
    }
	if(length < minlength){
    	minlength = length;
    	for(i = 0;i < n;i++){
    		mintour[i] = a[i];
    	}
    }
    if(length > maxlength){
    	maxlength = length;
    	for(i = 0; i < n; i++){
    		maxtour[i] = a[i];
    	}
    }

}

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

int main(int argc,char **argv)
{
  FILE *fp = fopen("tspGrafInfo", "w");
  int n_mec,special,n,i,a[max_n_cities];
  char file_name[32];
  double dt1, dt2;

  n_mec = 89318; // CHANGE THIS!
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
   //random
   /*for(int j = 1; j < 1000000; j++ ){
      	rand_perm(n, a);
   		dt2 += elapsed_time();
   	}
   printf("tsp_vrand() finished in %8.3fs (%ld tours generated)\n",dt2,n_tours); //3º
   	 printf("  rin %5d [",minlength); //4º
      for(i = 0;i < n;i++)//4º
        printf("%2d%s",mintour[i],(i == n - 1) ? "]\n" : ",");

    printf("  rax %5d [",maxlength);
      for(i = 0;i < n;i++)
       printf("%2d%s",maxtour[i],(i == n - 1) ? "]\n" : ",");
*/
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
