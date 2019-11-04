package main

import (
	"fmt"
	"sync"
	"sync/atomic"
	"time"
)

type T = struct {
	id int
	c  rune
}

var cubiertos = make(chan rune, 2)

var messages [5]chan T
var status [5]chan string

var cantidad int32 = 10

var l1 string = "2334400112"
var l2 = []byte{'.', '.', '.', '.', '.', '.', '.', '.', '.', '.', 0}
var l3 string = "0011223344"

var mutex = &sync.Mutex{}

func f(id int) {
	for {

		// Si tengo mensajes me los como
		select {
		case msg := <-messages[id]:
			c := atomic.AddInt32(&cantidad, -1)
			i := id + msg.id + (func() int {
				if msg.id < id {
					return -2
				} else {
					return 3
				}
			})()
			mutex.Lock()
			fmt.Println("           ", c)
			l2[i] = string(msg.c)[0]
			fmt.Println(l1)
			fmt.Println(fmt.Sprintf("%s", l2))
			fmt.Println(l3)
			mutex.Unlock()

			time.Sleep(time.Second) //Comiendo

			mutex.Lock()
			l2[i] = '.'
			mutex.Unlock()

			status[id] <- "ready"
		default:
		}

		// Si tengo cuchara
		select {
		case cuchara := <-cubiertos:
			// Envio un mensaje
			h := -1
			select {
			case <-status[(id+2)%5]:
				h = (id + 2) % 5
			case <-status[(id+3)%5]:
				h = (id + 3) % 5
			default:
			}
			//fmt.Println(h)
			messages[h] <- T{id, cuchara}
			status[h] <- <-status[h]
			cubiertos <- cuchara

		default:
		}

	}
	fmt.Println("f", id, "exit")

}

func main() {

	cubiertos <- 'a'
	cubiertos <- 'b'

	for i := 0; i <= 4; i++ {
		messages[i] = make(chan T, 1)
		status[i] = make(chan string, 1)
		status[i] <- "ready" //antes de lanzar la rutina

	}

	for w := 0; w <= 4; w++ {
		go f(w)
	}
	for atomic.LoadInt32(&cantidad) > 0 { //Mala sincronización
		//refactoring
	}
	fmt.Println("end")

}
