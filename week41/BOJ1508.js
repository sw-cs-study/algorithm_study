const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const [N, M, K] = input[0].split(" ").map(Number);
const points = input[1].split(" ").map(Number);

let [l, r] = [1, N + 1];

while (l + 1 < r) {
  const dist = Math.floor((l + r) / 2);

  let number = check(dist);
  if (number < M) {
    r = dist;
  } else {
    l = dist;
  }
}

const fixedDist = l;
const state = Array(K).fill(0);
let rest = M;
let cur = -1;

points.forEach((v, i) => {
  if (rest === 0) return;
  if (cur === -1) {
    cur = v;
    rest--;
    state[i] = 1;
    return;
  }

  if (v < cur + fixedDist) return;
  cur = v;
  rest--;
  state[i] = 1;
});

console.log(state.join(""));

// 최대 거리기 들어왔을 때, 미리 정해진 곳 중 배치할 수 있는 심판의 수를 반환
function check(d) {
  let number = 0;
  let cur = -1;

  points.forEach((v, i) => {
    if (cur === -1) {
      cur = v;
      number++;
      return;
    }

    if (v < cur + d) return;
    number++;
    cur = v;
  });

  return number;
}
