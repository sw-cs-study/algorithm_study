// 10^9 이하의 수에서 최대 3의 제곱수는 3^18, 최대 2의 제곱수는 2^29
// 카카오 풀이에서 이해안되는 문장이 있음. 명제 1의 마지막 부분에 "항상 윗층의 리프 노드가 더 작은 경로곱을 가지기 때문에, a를 늘리고 b를 줄이는 것이 경로곱 제한을 악화하지 않습니다."

function solution(dist_limit, split_limit) {
  var answer = 0;
  let Max2Pow = 29;
  let Max3Pow = 18;

  let kByDepth; // kByDepth: 현재 층의 분배노드의 분배수

  const dfs = (depth = 0, rest = dist_limit + 1, leaves = 1) => {
    // 자식 노드가 2개가 되도록 먼저 만들고 나머지는 3개로 만들기(depth로 kByDepth 조회해서 현재 몇의 분배수를 가진 분배노드를 생성할 지 결정)
    // 생성 가능한 분배노드수(rest)가 모자라면 부분 분배하고 리프 노드 수 구해서 답과 비교
    if (depth === kByDepth.length) {
      // rest >= 0이어도 현재 층의 모든 노드는 리프 노드
      if (leaves > answer) answer = leaves;
      return;
    }

    let k = kByDepth[depth];

    if (leaves <= rest) {
      // 완전 분배
      dfs(depth + 1, rest - leaves, leaves * k);
    } else {
      // 부분 분배
      let v = leaves - rest + rest * k;
      if (v > answer) answer = v;
      return;
    }

    return;
  };

  for (let i = 0; i < Max2Pow + 1; i++) {
    for (let j = 0; j < Max3Pow + 1; j++) {
      // 경로곱 컷
      if (Math.pow(2, i) * Math.pow(3, j) > split_limit) continue;

      kByDepth = [...Array(1 + i + j)].map((_, idx) => {
        if (idx === 0) return 1;
        if (idx < i + 1) return 2;
        return 3;
      });

      dfs();
    }
  }

  return answer;
}
