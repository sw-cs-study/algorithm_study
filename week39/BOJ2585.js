const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const [n, k] = input[0].split(" ").map(Number);
const points = [];
points.push([0, 0]);
for (let i = 0; i < n; i++) {
  let [x, y] = input[1 + i].split(" ").map(Number);
  points.push([x, y]);
}
points.push([10000, 10000]);

// 매개변수 탐색으로 연료통의 크기를 정한 후 해당 연료통으로 목적지에 도달할 수 있는지 체크
let [l, r] = [-1, 10000];
while (l + 1 < r) {
  let mid = Math.floor((l + r) / 2);

  if (check(mid)) {
    r = mid;
  } else {
    l = mid;
  }
}
console.log(r);

function check(fuel) {
  let queue = [[0, 0]]; // point index, count

  let visited = new Uint8Array(points.length);
  visited[0] = 1;
  let maxD = fuel * 10;

  while (queue.length > 0) {
    let [curIdx, count] = queue.shift();

    for (let i = 0; i < points.length; i++) {
      let [nx, ny] = points[i];
      if (i === curIdx) continue;
      if (visited[i]) continue;

      let d = getDistance(curIdx, i);
      if (d > maxD) continue;
      if (nx === 10000 && ny === 10000) return true;
      if (count + 1 > k) continue;
      visited[i] = 1;
      queue.push([i, count + 1]);
    }
  }
  return false;
}

function getDistance(a, b) {
  return Math.sqrt(
    (points[a][0] - points[b][0]) ** 2 + (points[a][1] - points[b][1]) ** 2,
  );
}
