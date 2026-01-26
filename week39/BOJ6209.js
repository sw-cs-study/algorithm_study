const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const [d, n, m] = input[0].split(" ").map(Number);
const stones = [];
for (let i = 0; i < n; i++) {
  stones.push(Number(input[1 + i]));
}
stones.sort((a, b) => a - b);

let [l, r] = [1, d + 1];
while (l + 1 < r) {
  let mid = Math.floor((l + r) / 2);

  let now = 0;
  let removedStone = 0;
  // mid 가 현재 길을 갈 때의 최솟값이라고 조건을 걸었기 때문에, 돌을 넘을 때 거리가 mid이하라면 돌을 제거함
  for (let nextStone of stones) {
    let distance = nextStone - now;
    if (distance < mid) {
      removedStone++;
    } else {
      now = nextStone;
    }
  }
  // 목적지 넘어갈 때 거리에 대한 로직
  if (d - now < mid) {
    removedStone++;
  }

  // 비교
  if (removedStone > m) {
    r = mid;
  } else {
    // removedStone <== mid
    l = mid;
    answer = mid;
  }
}
console.log(answer);
