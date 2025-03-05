const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "sample.txt")
  .toString()
  .trim()
  .split("\n");

// 특정 등수 앞에 확정적으로 몇명있는지(최소등수)
// 특정 등수 뒤에 확정적으로 몇명있는지(최대등수)
// 등수는 사람수를 다 세야함
const [n, m, x] = input[0].split(" ").map(Number);
const forward = [...Array(n + 1)].map(() => []);
const backward = [...Array(n + 1)].map(() => []);

for (let i = 0; i < m; i++) {
  let [a, b] = input[1 + i].split(" ").map(Number);
  forward[b].push(a);
  backward[a].push(b);
}

let visited = Array(n + 1).fill(false);
let higherRank = 1 + countHigherPerson(x);
visited = Array(n + 1).fill(false);
let lowerRank = n - countLowerPerson(x);
console.log(higherRank, lowerRank);

function countHigherPerson(cur) {
  visited[cur] = true;
  if (forward[cur].length === 0) {
    return 0;
  }
  let cnt = 0;
  for (let pre of forward[cur]) {
    if (visited[pre]) {
      continue;
    }
    cnt += 1 + countHigherPerson(pre);
  }

  return cnt;
}

function countLowerPerson(cur) {
  visited[cur] = true;
  if (backward[cur].length === 0) {
    return 0;
  }

  let cnt = 0;
  for (let post of backward[cur]) {
    if (visited[post]) {
      continue;
    }
    cnt += 1 + countLowerPerson(post);
  }

  return cnt;
}
