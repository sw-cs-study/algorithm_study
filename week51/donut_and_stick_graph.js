// 생성한 정점을 판별할수 있나? => 일단 들어오는 간선은 없음, 나가는 간선이 최소 2개
// 도넛 모양 => 한 정점에서 나가는 간선 1개 + 들어오는 간선 1개
//         => 막대랑 8자 뺀 나머지
// 막대 => 나가는 정점 하나 + 들어오고 나가는 정점 여러개 + 들어오는 정점 1개
//     => 들어오는 거 하나만 있는 정점 갯수
// 8자 => 한 정점에서 나가는 간선 1개 + 들어오는 간선 1개 => 허리 정점은 나가는거 2개 + 들어오는거 2개
//    => 나가는거 2개 + 들어오는거 2개 갯수
// 막대모양이랑 도넛 모양 판별이 애매함. 8자는 무조건 나가는간선 1개 들어오는 간선 1개로 판별가능 (먼저 세기)

function solution(edges) {
  var answer = [0, 0, 0, 0];
  let maxNum = 0;
  for (let [a, b] of edges) {
    maxNum = Math.max(a, b, maxNum);
  }
  let board = [...Array(maxNum + 1)].map(() => [0, 0]); // [나가는 간선, 들어오는 간선]
  let pointBoard = [...Array(maxNum + 1)].map(() => false);
  for (let [a, b] of edges) {
    board[a][0] += 1;
    board[b][1] += 1;
    pointBoard[a] = true;
    pointBoard[b] = true;
  }
  let pointNum = 0;
  for (let bool of pointBoard) {
    if (bool) {
      pointNum += 1;
    }
  }
  let graphNum = 0; // 그래프 갯수
  for (let i = 0; i < board.length; i++) {
    if (board[i][0] > 1 && board[i][1] == 0) {
      // 생성된 정점
      answer[0] = i;
      graphNum = board[i][0];
      continue;
    }
    // 8자, 들어오는 거 생성된 정점에서도 들어오면 3개 주의
    if (board[i][0] == 2 && board[i][1] >= 2) answer[3] += 1;

    // 막대, 들어오는 거 생성된 정점에서 들어오면 2개 주의
    if (board[i][0] == 0 && board[i][1] > 0) answer[2] += 1;
  }
  // 도넛
  answer[1] = graphNum - answer[2] - answer[3];

  return answer;
}
