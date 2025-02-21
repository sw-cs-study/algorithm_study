const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "sample.txt")
  .toString()
  .trim()
  .split("\n");

const [n, k] = input[0].split(" ").map(Number);
if (n === k) {
  console.log(`0\n1`);
} else {
  const queue = [k];
  let time = 1;
  let cnt = 0;
  let visited = Array(100001).fill(0); // 경우의 수 저장
  visited[k] = 1;
  let tempV = [...visited];
  while (queue.length > 0) {
    let size = queue.length;
    cnt = 0;
    let arrived = false;
    let toCalculate = [];
    for (let i = 0; i < size; i++) {
      let cur = queue.shift();
      // 첫 방문에만 push
      if (visited[cur - 1] === 0) {
        queue.push(cur - 1);
      }
      visited[cur - 1] += tempV[cur];
      toCalculate.push(cur - 1);
      //
      if (visited[cur + 1] === 0) {
        queue.push(cur + 1);
      }
      visited[cur + 1] += tempV[cur];
      toCalculate.push(cur + 1);
      //
      if (cur % 2 === 0 && visited[cur / 2] === 0) {
        queue.push(cur / 2);
      }
      if (cur % 2 === 0) {
        visited[cur / 2] += tempV[cur];
        toCalculate.push(cur / 2);
      }
      if (cur - 1 === n || cur + 1 === n || (cur % 2 === 0 && cur / 2 === n)) {
        arrived = true;
      }
    }
    if (arrived) {
      cnt = visited[n];
      break;
    }
    // tempV 에 최신 정보 업데이트
    for (let p of toCalculate) {
      tempV[p] = visited[p];
    }
    time += 1;
  }
  console.log(`${time}\n${cnt}`);
}
