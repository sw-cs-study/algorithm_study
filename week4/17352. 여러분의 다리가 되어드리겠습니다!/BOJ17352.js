const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "sample.txt")
  .toString()
  .trim()
  .split("\n");

const n = Number(input[0]);
const parent = [...Array(n)].map((_, i) => i);
for (let i = 0; i < n - 2; i++) {
  let [a, b] = input[1 + i].split(" ").map((v) => Number(v) - 1);
  union(a, b);
}

const num = find(0);
for (let i = 1; i < n; i++) {
  if (num !== find(i)) {
    console.log(1, i + 1);
    break;
  }
}

function union(x, y) {
  x = find(x);
  y = find(y);
  if (x > y) {
    parent[x] = y;
  } else {
    parent[y] = x;
  }
}

function find(x) {
  if (x === parent[x]) {
    return x;
  }
  let ret = find(parent[x]);
  parent[x] = ret;
  return ret;
}
