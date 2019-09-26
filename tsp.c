//
// José Frias
// Rodrigo Oliveira
// ...
//
// AED, 2018/2019
//
// solution of the traveling salesman problem
//

#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/time.h>
#include <sys/resource.h>
#include "cities.h"
#include "elapsed_time.h"


//
// record best solutions
//

static int min_length,max_length;
static int min_tour[max_n_cities + 1],max_tour[max_n_cities + 1];
static long n_tours;
long hist[10001];

//
// first solution (brute force, distance computed at the end, compute best and worst tours)
//


void tsp_v1(int n,int m,int *a)
{
  int i,t;

  if(m < n - 1)
    for(i = m;i < n;i++)
    {
      t = a[m];
      a[m] = a[i];
      a[i] = t;
      tsp_v1(n,m + 1,a);
      t = a[m];
      a[m] = a[i];
      a[i] = t;
    }
  else
  { // visit permutation
    n_tours++;
    int dist=0;
    // modify the following code to do your stuff
    for(i = 0;i < n;i++){


            if(i < n-1){

                    dist += cities[a[i]].distance[a[i+1]];
            }
            else{
                    dist += cities[a[i]].distance[a[0]];
            }
           // printf("%d %s ", a[i], (i==n-1)? "\n" : "");
    }


              if (max_length<dist){
                    max_length=dist;
                    for(i=0; i<n; i++){
                       max_tour[i]=a[i];
                       }
                 }

              if(min_length>dist){
                    min_length =dist;
                    for(i=0; i<n;i++){
                        min_tour[i]=a[i];
                            }
                   }
              hist[dist]++;



  }

}
/*
void tsp_v0(int n,int m,int *a)
{
  int i,t;


  for (t = 0; t < 1000000; t++) {
    // modify the following code to do your stuff
    srand(time(NULL));

    for (int c = n-1; c > 0; c--){
    //generate a random number [0, n-1]
    int j = rand() % (c+1);

    //swap the last element with element at random index
    int temp = a[c];
    a[c] = a[j];
    a[j] = temp;
    }

    int tour_dist = 0;

    for(i = 0;i < n;i++){
      if (i < n-1) {
        tour_dist += cities[a[i]].distance[a[i+1]];
         //code
      }
      else
        tour_dist += cities[a[i]].distance[a[0]];
      //printf("%d%s",a[i],(i == n - 1) ? "\n" : " ");
    }

    hist[tour_dist]++;

    //fprintf(tlen,"%d,", tour_dist);
    //printf("%d\n", tour_dist);
    }
}

*/

int main(int argc,char **argv)
{

  int n_mec,special,n,i,a[max_n_cities];
  char file_name[32];
  FILE *tlen;
  double dt1;

  tlen=fopen("Special1distances.txt", "w+");

  n_mec = 89206; // CHANGE THIS!
  special = 1;
  init_cities_data(n_mec,special);
  printf("data for init_cities_data(%d,%d)\n",n_mec,special);
  fflush(stdout);
#if 1
  print_distances();
#endif
  for(n = 12;n <= 15;n+=3)
  {
    //
    // try tsp_v1
    //
    dt1 = -1.0;
    if(n <= 15)
    {

      fprintf(tlen, "Tours with %d cities\n", n );
      (void)elapsed_time();
      for(i = 0;i < n;i++)
        a[i] = i;
      min_length = 100000000;
      max_length = 0;
      n_tours = 0l;
      tsp_v1(n, 1, a);
      //tsp_v0(n,1,a); // no need to change the starting city, as we are making a tour
      dt1 = elapsed_time();
      printf("tsp_v1() finished in %8.3fs (%ld tours generated)\n",dt1,n_tours);
      printf("  min %5d [",min_length);
      for(i = 0;i < n;i++)
        printf("%2d%s",min_tour[i],(i == n - 1 ) ? "]\n" : ",");
      printf("  max %5d [",max_length);
      for(i = 0;i < n;i++)
        printf("%2d%s",max_tour[i],(i == n - 1 ) ? "]\n" : ",");
      for(i=0; i<10001; i++){
              if(hist[i] !=0){
                      fprintf(tlen,"hist(%d)=%ld;\n",i, hist[i]);
              }
      }

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


  return 0;
}
