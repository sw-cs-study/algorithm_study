const input = require('fs').readFileSync(process.platform === "linux" ? '/dev/stdin' : 'sample.txt').toString().trim().split('\n');

const n = Number(input[0]);
const matrixes = [];
for (let i = 0; i < n; i++) {
  matrixes.push(input[1 + i].split(" ").map(Number));
}
const visited = [];
for (let i = 0; i < n; i++) {
  visited.push([]);
  for (let j = 0; j < n; j++) {
    visited[i].push((i === j ? 0 : Infinity));
  }
}

console.log(recur());


function recur(s = 0, e = n - 1) {
  if (visited[s][e] !== Infinity) { return visited[s][e]; }
  if (e - s === 1) {
    visited[s][e] = matrixes[s][0] * matrixes[s][1] * matrixes[e][1];
    return visited[s][e];
  }

  let minValue = Infinity;
  for (let i = s; i < e; i++) {
    let ret = recur(s, i) + recur(i + 1, e) + matrixes[s][0] * matrixes[i][1] * matrixes[e][1];
    if (ret < minValue) { minValue = ret; }
  }
  visited[s][e] = minValue;
  return visited[s][e];
}