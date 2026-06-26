/**
 * 메모이제이션으로 안익은 토마토에서 가장 가까운 익은 토마토 까지의 거리를 구하고 그중 가장 큰 거리를 출력
 */

let [[m, n, o, p, q, r, s, t, u, v, w], ...arr] = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const delta = [
  [1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
  [-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
  [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0],
  [0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0],
  [0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0],
  [0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0],
  [0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0],
  [0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0],
  [0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0],
  [0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0],
  [0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0],
  [0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0],
  [0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0],
  [0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0],
  [0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0],
  [0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0],
  [0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0],
  [0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0],
  [0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0],
  [0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0],
  [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1],
  [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1],
];

const notRipe = [];

const board = [];
let filler = 0;
for (let a = 0; a < w; a++) {
  let ten = [];
  for (let b = 0; b < v; b++) {
    let nine = [];
    for (let c = 0; c < u; c++) {
      let eight = [];
      for (let d = 0; d < t; d++) {
        let seven = [];
        for (let e = 0; e < s; e++) {
          let six = [];
          for (let f = 0; f < r; f++) {
            let five = [];
            for (let g = 0; g < q; g++) {
              let four = [];
              for (let h = 0; h < p; h++) {
                let three = [];
                for (let i = 0; i < o; i++) {
                  let two = [];
                  for (let j = 0; j < n; j++) {
                    two.push(arr[filler + j]);
                    for (let k = 0; k < m; k++) {
                      if (two[j][k] === 0) {
                        notRipe.push([a, b, c, d, e, f, g, h, i, j, k]);
                      }
                    }
                  }
                  filler += n;
                  three.push(two);
                }
                four.push(three);
              }
              five.push(four);
            }
            six.push(five);
          }
          seven.push(six);
        }
        eight.push(seven);
      }
      nine.push(eight);
    }
    ten.push(nine);
  }
  board.push(ten);
}

console.log(board, arr);
