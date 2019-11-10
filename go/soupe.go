package main

//ERROR
// fatal error: all goroutines are asleep - deadlock!
// A veces da ese error ¿?¿?

import (
	"fmt"
	"sync"
	"sync/atomic"
	"time"
)

// T estructura de comida
// id filosofo que da de comer
// y c es la cuchar
type T = struct {
	id int
	c  rune
}

const N = 5

// Se implementan las cucharas como valores en un canal
// Si están en él se pueden coger, y si no a esperar
var cubiertos = make(chan rune, 2)

// Canal por el que cada filosofo informa de su estado
// Que no es otro que estar hambriento, o esperando a que
// alguien le de de comer
var status [N]chan string

// Canal por el que recibe comida
// Que no es otra cosa que información de quien le está dando
// de comer y con qué cuchara
var messages [N]chan T

var cantidad int32 = 10

var l1 string = "2334400112"
var l2 = []byte{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}
var l3 string = "0011223344"

var mutex = &sync.Mutex{}
var wg sync.WaitGroup

func f(id int) {
	for atomic.LoadInt32(&cantidad) > 0 {

		// AAaAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
		// Si tengo mensajes me los como
		// Alguien ha cogido mi mensaje 'ready' y tiene comida para mi
		select {
		case msg := <-messages[id]:

			// tontería para calcular en que posición de la linea
			// voy a pintar la cuchara. este codigo es feo.
			i := id + msg.id + (func() int {
				if msg.id < id {
					return -2
				} else {
					return 3
				}
			})()

			// Proceso de impresión
			// Con mutex para evitar que se mezclen lineas impresas
			// por distintos filosofos
			mutex.Lock()
			fmt.Println("           ", atomic.AddInt32(&cantidad, -1))
			l2[i] = string(msg.c)[0] // pongo la cuchara en la posición
			fmt.Println(l1)
			fmt.Println(fmt.Sprintf("%s", l2))
			fmt.Println(l3)
			//fmt.Println("\033[5A") // Escape para sobre escribir la consola
			mutex.Unlock()

			//Comiendo:
			// No en mutex. Se sobre imprimen las lineas
			// Si utilizamos la secuencia de escape
			time.Sleep(time.Second) //Comiendo

			mutex.Lock()
			// Es posible que otro filosofo
			// pueda intentar decir que está dando de comer con la cuchara x
			// sobre escribiendo esta variable
			l2[i] = '.'
			mutex.Unlock()

			// Ya estoy comido y vuelvo a estar 'ready'
			status[id] <- "ready"
		default:
		}

		// BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB
		// Si consigo cuchara
		select {
		case cuchara := <-cubiertos:
			// Envio un mensaje a h
			// Es decir le doy de comer
			// y averiguo a quien le puedeo dar
			// leyendo en paralelo de los dos canales
			// de los filosofos a los que alimento
			h := -1
			select {
			case <-status[(id+2)%5]:
				h = (id + 2) % 5
			case <-status[(id+3)%5]:
				h = (id + 3) % 5
			default:
			}
			//fmt.Println(h)
			messages[h] <- T{id, cuchara} //Toma sopa

			// Espero a recibir que está 'ready' y lo vuelvo a poner
			// en su canal para que continue el ciclo
			status[h] <- <-status[h]

			// devuelvo la cuchara
			cubiertos <- cuchara
		default:
		}

	}
	close(messages[id])
	close(status[id])
	defer wg.Done()
	fmt.Println("f", id, "exit")

}

func main() {

	// Canal de cubiertos, 2
	cubiertos <- 'a'
	cubiertos <- 'b'

	wg.Add(N)

	for i := 0; i <= N-1; i++ {
		// Canal de comida por cada filosofo
		messages[i] = make(chan T, 1)
		status[i] = make(chan string, 1)
		// Y todos están 'ready' para comer
		status[i] <- "ready" //antes de lanzar la gorutina
	}

	for w := 0; w <= N-1; w++ {
		go f(w)
	}

	wg.Wait()
	close(cubiertos)
	fmt.Println("end")
}
