// 순서마다 몇번 파이프를 개방할지 중복조합을 구하고, 구한 중복조합의 파이프가 현재까지 감염된 배양체와 감염되지 않은 배양체를 연결하는지 확인하고 감염되지 않았다면 감염시키고 감염된 배양체 목록에 추가해나감.
// 완탐을 돌리면 3^10 * n * k = 59049 * 1000 정도라 가능할듯

function solution(n, infection, edges, k) {
  const permutation = (number) => {
    if (number === 0) return [[]];

    let ret = [];
    for (let i = 1; i < 4; i++) {
      for (let result of permutation(number - 1)) {
        ret.push([...result, i]);
      }
    }

    return ret;
  };

  let infos = [...Array(n + 1)].map(() => []);
  edges.forEach((line, i) => {
    let [a, b, type] = line;
    infos[a].push([b, type]);
    infos[b].push([a, type]);
  });

  let answer = 0;
  for (let order of permutation(k)) {
    let cnt = 1;
    let visited = [...Array(n + 1)].fill(false);
    visited[infection] = true;
    let infected = [infection]; // 감염체들 계속 저장

    for (let pipe of order) {
      for (let infection of infected) {
        for (let [cell, type] of infos[infection]) {
          if (visited[cell]) continue;
          if (pipe !== type) continue;
          visited[cell] = true;
          infected.push(cell);
          cnt++;
        }
      }
    }
    if (answer < cnt) answer = cnt;
    if (answer === n) break;
  }

  return answer;
}
